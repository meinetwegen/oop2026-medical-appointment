package edu.aitu.oop3.services;

import edu.aitu.oop3.exceptions.*;
import edu.aitu.oop3.models.Appointment;
import edu.aitu.oop3.repositories.interfaces.IAppointmentRepository;
import edu.aitu.oop3.repositories.interfaces.IDoctorRepository;
import edu.aitu.oop3.repositories.interfaces.IPatientRepository;

import java.util.stream.Stream;
import java.sql.SQLException;
import java.util.List;

public class AppointmentService {
    private final IAppointmentRepository appointmentRepo;
    private final IDoctorRepository doctorRepo;
    private final IPatientRepository patientRepo;
    private final DoctorAvailabilityService availabilityService;

    public AppointmentService(IAppointmentRepository appointmentRepo,
                              IDoctorRepository doctorRepo,
                              IPatientRepository patientRepo,
                              DoctorAvailabilityService availabilityService) {
        this.appointmentRepo = appointmentRepo;
        this.doctorRepo = doctorRepo;
        this.patientRepo = patientRepo;
        this.availabilityService = availabilityService;
    }

    public void bookAppointment(Appointment app)
            throws DoctorUnavailableException, TimeSlotAlreadyBookedException, SQLException, AppointmentNotFoundException {

        if (doctorRepo.findById(app.getDoctorId()) == null) {
            throw new AppointmentNotFoundException(app.getDoctorId());
        }

        if (patientRepo.findById(app.getPatientId()) == null) {
            throw new AppointmentNotFoundException(app.getPatientId());
        }

        availabilityService.checkAvailability(app.getDoctorId(), app.getAppointmentTime());
        appointmentRepo.add(app);
    }

    public void cancelAppointment(int id) throws AppointmentNotFoundException, SQLException {
        if (appointmentRepo.findById(id) == null) {
            throw new AppointmentNotFoundException(id);
        }
        appointmentRepo.cancel(id);
    }

    public List<Appointment> getDoctorSchedule(int doctorId) throws SQLException {
        return appointmentRepo.findByDoctorId(doctorId);
    }

    public List<Appointment> getPatientVisits(int patientId) throws SQLException {
        return appointmentRepo.findByPatientId(patientId);
    }

    public long getActiveAppointmentsCount() {
        try {
            List<Appointment> allApps = appointmentRepo.findAll();
            return allApps.stream()
                    .filter(a -> "scheduled".equalsIgnoreCase(a.getStatus()))
                    .count();
        } catch (Exception e) {
            return 0;
        }
    }
}