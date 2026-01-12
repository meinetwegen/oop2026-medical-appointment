//для поиска врачей
package edu.aitu.oop3.repositories;

import edu.aitu.oop3.models.Doctor;
import java.sql.SQLException;
import java.util.List;

public interface IDoctorRepository {
    void add(Doctor doctor) throws SQLException;
    Doctor findById(int id) throws SQLException;
    List<Doctor> findAll() throws SQLException;
}