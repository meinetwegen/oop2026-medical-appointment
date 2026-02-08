//Интерфейсы определяют, что приложение может делать с базой данных, не уточняя, как именно (через PostgreSQL, MySQL или файл).
//создание записей, их отмена и получение списков для расписания
package edu.aitu.oop3.components.scheduling;

import edu.aitu.oop3.shared.IRepository;

import java.sql.SQLException;
import java.util.List;

public interface IAppointmentRepository extends IRepository<Appointment> {
    void cancel(int id) throws SQLException;
    List<Appointment> findByDoctorId(int doctorId) throws SQLException;     //для расписания врача
    List<Appointment> findByPatientId(int patientId) throws SQLException;   //для upcoming visits
    boolean isSlotTaken(int doctorId, java.time.LocalDateTime time) throws SQLException;    //проверка занятости времени
}