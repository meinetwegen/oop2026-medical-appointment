package edu.aitu.oop3.services;

import edu.aitu.oop3.exceptions.DoctorUnavailableException;
import edu.aitu.oop3.exceptions.TimeSlotAlreadyBookedException;
import edu.aitu.oop3.repositories.interfaces.IAppointmentRepository;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DoctorAvailabilityService {
    private final IAppointmentRepository appointmentRepo;

    public DoctorAvailabilityService(IAppointmentRepository appointmentRepo) {
        this.appointmentRepo = appointmentRepo;
    }

    public void checkAvailability(int doctorId, LocalDateTime time)
            throws DoctorUnavailableException, TimeSlotAlreadyBookedException, SQLException {

        if (time.getHour() < 9 || time.getHour() >= 18) {       //проверка рабочих часов
            throw new DoctorUnavailableException(doctorId, time);
        }

        if (appointmentRepo.isSlotTaken(doctorId, time)) {      //слот времени занят?
            throw new TimeSlotAlreadyBookedException(time);
        }
    }
}