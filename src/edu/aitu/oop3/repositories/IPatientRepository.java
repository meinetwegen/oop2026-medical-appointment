//проверка существования + поиск пациента
package edu.aitu.oop3.repositories;

import edu.aitu.oop3.models.Patient;

import java.sql.SQLException;

public interface IPatientRepository {
    void add(Patient patient) throws SQLException;
    Patient findByEmail(String email) throws SQLException;
    Patient findById(int id) throws SQLException;
}