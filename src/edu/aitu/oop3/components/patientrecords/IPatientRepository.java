//проверка существования + поиск пациента
package edu.aitu.oop3.components.patientrecords;

import edu.aitu.oop3.shared.IRepository;

import java.sql.SQLException;

public interface IPatientRepository extends IRepository<Patient> {
    Patient findByEmail(String email) throws SQLException;
}

