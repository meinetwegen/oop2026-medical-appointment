package edu.aitu.oop3.services;

import edu.aitu.oop3.exceptions.*;
import edu.aitu.oop3.models.Appointment;
import edu.aitu.oop3.repositories.IAppointmentRepository;
import java.sql.SQLException;
import java.util.List;

public class AppointmentService {
    private final IAppointmentRepository appointmentRepo;
    private final DoctorAvailabilityService availabilityService;

    public AppointmentService(IAppointmentRepository appointmentRepo, DoctorAvailabilityService availabilityService) {
        this.appointmentRepo = appointmentRepo;
        this.availabilityService = availabilityService;
    }

    public void bookAppointment(Appointment app)        //регистрация новой записи
            throws DoctorUnavailableException, TimeSlotAlreadyBookedException, SQLException {

        availabilityService.checkAvailability(app.getDoctorId(), app.getAppointmentTime());     //доктор свободен?

        appointmentRepo.add(app);   //всё нормальн => в базу
    }

    public void cancelAppointment(int id) throws AppointmentNotFoundException, SQLException {   //отмена записи
        if (appointmentRepo.findById(id) == null) {
            throw new AppointmentNotFoundException(id);
        }
        appointmentRepo.cancel(id);
    }

    public List<Appointment> getDoctorSchedule(int doctorId) throws SQLException {      //расписание врача
        return appointmentRepo.findByDoctorId(doctorId);
    }
}