//Интерфейсы определяют, что приложение может делать с базой данных, не уточняя, как именно (через PostgreSQL, MySQL или файл).
//создание записей, их отмена и получение списков для расписания
package edu.aitu.oop3.repositories.interfaces;

import edu.aitu.oop3.models.Appointment;
import java.sql.SQLException;
import java.util.List;

public interface IAppointmentRepository {
    void add(Appointment appointment) throws SQLException;
    void cancel(int id) throws SQLException;
    Appointment findById(int id) throws SQLException;

    List<Appointment> findByDoctorId(int doctorId) throws SQLException;     //для расписания врача

    List<Appointment> findByPatientId(int patientId) throws SQLException;   //для upcoming visits

    boolean isSlotTaken(int doctorId, java.time.LocalDateTime time) throws SQLException;    //проверка занятости времени
}