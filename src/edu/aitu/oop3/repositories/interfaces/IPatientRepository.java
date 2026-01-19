//проверка существования + поиск пациента
package edu.aitu.oop3.repositories.interfaces;

import edu.aitu.oop3.models.Patient;

import java.sql.SQLException;
import java.util.List;

public interface IPatientRepository {
    void add(Patient patient) throws SQLException;

    Patient findByEmail(String email) throws SQLException;

    Patient findById(int id) throws SQLException;

    List<Patient> findAll() throws SQLException;
}