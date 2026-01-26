package edu.aitu.oop3.repositories;

import edu.aitu.oop3.db.IDB;
import edu.aitu.oop3.models.Appointment;
import edu.aitu.oop3.repositories.interfaces.IAppointmentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresAppointmentRepository implements IAppointmentRepository {
    private final IDB db;

    public PostgresAppointmentRepository(IDB db) {
        this.db = db;
    }

    @Override
    public void add(Appointment app) throws SQLException {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, time_slot, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, app.getPatientId());
            stmt.setInt(2, app.getDoctorId());
            stmt.setTimestamp(3, Timestamp.valueOf(app.getAppointmentTime()));
            stmt.setString(4, app.getStatus());
            stmt.executeUpdate();
        }
    }

    @Override
    public void cancel(int id) throws SQLException {
        String sql = "UPDATE appointments SET status = 'cancelled' WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Appointment findById(int id) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Appointment.Builder()
                            .setId(rs.getInt("id"))
                            .setPatientId(rs.getInt("patient_id"))
                            .setDoctorId(rs.getInt("doctor_id"))
                            .setAppointmentTime(rs.getTimestamp("time_slot").toLocalDateTime())
                            .setStatus(rs.getString("status"))
                            .build();
                }
            }
        }
        return null;
    }

    @Override
    public List<Appointment> findAll() throws SQLException {
        return List.of();
    }

    @Override
    public List<Appointment> findByDoctorId(int doctorId) throws SQLException {
        return findByCriteria("SELECT * FROM appointments WHERE doctor_id = ?", doctorId);
    }

    @Override
    public List<Appointment> findByPatientId(int patientId) throws SQLException {
        return findByCriteria("SELECT * FROM appointments WHERE patient_id = ?", patientId);
    }

    private List<Appointment> findByCriteria(String sql, int id) throws SQLException {
        List<Appointment> apps = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Appointment app = new Appointment.Builder()
                            .setId(rs.getInt("id"))
                            .setPatientId(rs.getInt("patient_id"))
                            .setDoctorId(rs.getInt("doctor_id"))
                            .setAppointmentTime(rs.getTimestamp("time_slot").toLocalDateTime())
                            .setStatus(rs.getString("status"))
                            .build();
                    apps.add(app);
                }
            }
        }
        return apps;
    }

    @Override
    public boolean isSlotTaken(int doctorId, java.time.LocalDateTime time) throws SQLException {
        String sql = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND time_slot = ? AND status != 'cancelled'";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            stmt.setTimestamp(2, Timestamp.valueOf(time));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }
}