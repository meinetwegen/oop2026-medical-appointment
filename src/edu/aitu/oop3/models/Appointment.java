package edu.aitu.oop3.models;

import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private int patientId;
    private int doctorId;
    private LocalDateTime appointmentTime;
    private String status;

    public Appointment (int patientId, int doctorId, LocalDateTime appointmentTime, String status){
        setPatientId(patientId);
        setDoctorId(doctorId);
        setAppointmentTime(appointmentTime);
        setStatus(status);
    }

    public Appointment (int id, int patientId, int doctorId, LocalDateTime appointmentTime, String status){
        this.id = id;
        setPatientId(patientId);
        setDoctorId(doctorId);
        setAppointmentTime(appointmentTime);
        setStatus(status);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        if (doctorId < 1) throw new IllegalArgumentException("Doctor ID must be positive");
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        if (patientId < 1) throw new IllegalArgumentException("Patient ID must be positive");
        this.patientId = patientId;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        if (appointmentTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Appointment time cannot be in the past.");
        }
        this.appointmentTime = appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null || status.isEmpty()) throw new IllegalArgumentException("Status cannot be empty");
        this.status = status;
    }

    @Override
    public String toString() {
        return "Appointment: id = " + id + ", patientId = " + patientId + ", doctorId = " + doctorId +
                ", time = " + appointmentTime + ", status = " + status;
    }
}