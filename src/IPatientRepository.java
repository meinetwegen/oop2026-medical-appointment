import java.sql.SQLException;

public interface IPatientRepository {
    void add(Patient patient) throws SQLException;
    Patient findByEmail(String email) throws SQLException;
}
