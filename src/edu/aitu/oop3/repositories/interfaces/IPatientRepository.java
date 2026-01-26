//проверка существования + поиск пациента
package edu.aitu.oop3.repositories.interfaces;

import edu.aitu.oop3.models.Patient;

import java.sql.SQLException;
import java.util.List;

public interface IPatientRepository extends IRepository<Patient> {
    Patient findByEmail(String email) throws SQLException;
}

