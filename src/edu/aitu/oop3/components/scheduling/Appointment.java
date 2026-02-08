package edu.aitu.oop3.components.scheduling;

import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private int patientId;
    private int doctorId;
    private LocalDateTime appointmentTime;
    private String status;

    private Appointment(Builder builder) {
        this.id = builder.id;
        setPatientId(builder.patientId);
        setDoctorId(builder.doctorId);
        setAppointmentTime(builder.appointmentTime);
        setStatus(builder.status);
    }

    public static class Builder {
        private int id;
        private int patientId;
        private int doctorId;
        private LocalDateTime appointmentTime;
        private String status = "Scheduled";

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setPatientId(int patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder setDoctorId(int doctorId) {
            this.doctorId = doctorId;
            return this;
        }

        public Builder setAppointmentTime(LocalDateTime appointmentTime) {
            this.appointmentTime = appointmentTime;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }


        public Appointment build() {
            return new Appointment(this);
        }
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) {
        if (doctorId < 1) throw new IllegalArgumentException("Doctor ID must be positive");
        this.doctorId = doctorId;
    }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) {
        if (patientId < 1) throw new IllegalArgumentException("Patient ID must be positive");
        this.patientId = patientId;
    }

    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalDateTime appointmentTime) {
        if (appointmentTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Appointment time cannot be in the past.");
        }
        this.appointmentTime = appointmentTime;
    }

    public String getStatus() { return status; }
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