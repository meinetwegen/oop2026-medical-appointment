//для поиска врачей
package edu.aitu.oop3.repositories.interfaces;

import edu.aitu.oop3.models.Doctor;
import java.sql.SQLException;
import java.util.List;

public interface IDoctorRepository extends IRepository<Doctor>{
    List<Doctor> findBySpecialization(String spec) throws SQLException;
}