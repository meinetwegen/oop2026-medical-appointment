//для поиска врачей
package edu.aitu.oop3.components.doctors;

import edu.aitu.oop3.shared.IRepository;

import java.sql.SQLException;
import java.util.List;

public interface IDoctorRepository extends IRepository<Doctor> {
    List<Doctor> findBySpecialization(String spec) throws SQLException;
}