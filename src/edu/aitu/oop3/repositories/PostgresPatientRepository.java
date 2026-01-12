package edu.aitu.oop3.repositories;

import edu.aitu.oop3.db.DatabaseConnection;
import edu.aitu.oop3.models.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresPatientRepository implements IPatientRepository {
    @Override
    public void add(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (full_name, phone, email) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, patient.getFullName());
            stmt.setString(2, patient.getPhoneNumber());
            stmt.setString(3, patient.getEmail());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) patient.setId(rs.getInt(1));
            }
        }
    }

    @Override
    public Patient findById(int id) throws SQLException {
        String sql = "SELECT * FROM patients WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Patient(rs.getInt("id"),
                                    rs.getString("full_name"),
                                    rs.getString("email"),
                                    rs.getString("phone"));
                }
            }
        }
        return null;
    }

    @Override
    public Patient findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM patients WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Patient(rs.getInt("id"),
                                    rs.getString("full_name"),
                                    rs.getString("email"),
                                    rs.getString("phone"));
                }
            }
        }
        return null;
    }

    @Override
    public List<Patient> findAll() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                patients.add(new Patient(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone")
                ));
            }
        }
        return patients;
    }
}