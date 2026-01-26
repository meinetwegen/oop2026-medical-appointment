package edu.aitu.oop3.repositories;

import edu.aitu.oop3.db.IDB;
import edu.aitu.oop3.models.Doctor;
import edu.aitu.oop3.repositories.interfaces.IDoctorRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresDoctorRepository implements IDoctorRepository {
    private final IDB db;

    public PostgresDoctorRepository(IDB db) {
        this.db = db;
    }

    @Override
    public void add(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO doctors (full_name, specialization) VALUES (?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, doctor.getFullName());
            stmt.setString(2, doctor.getSpecialization());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) doctor.setId(rs.getInt(1));
            }
        }
    }

    @Override
    public Doctor findById(int id) throws SQLException {
        String sql = "SELECT * FROM doctors WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Doctor(
                            rs.getInt("id"),
                            rs.getString("full_name"),
                            rs.getString("specialization")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Doctor> findAll() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors";
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                doctors.add(new Doctor(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("specialization")
                ));
            }
        }
        return doctors;
    }

    @Override
    public List<Doctor> findBySpecialization(String spec) throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors WHERE specialization ILIKE ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + spec + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                doctors.add(new Doctor(rs.getInt("id"), rs.getString("full_name"), rs.getString("specialization")));
            }
        }
        return doctors;
    }
}