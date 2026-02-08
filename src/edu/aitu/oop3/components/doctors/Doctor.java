package edu.aitu.oop3.components.doctors;

import edu.aitu.oop3.shared.User;

public class Doctor extends User {
    private String specialization;

    public Doctor(String fullName, String specialization) {
        super(fullName);
        setSpecialization(specialization);
    }

    public Doctor(int id, String fullName, String specialization) {
        super(id, fullName);
        setSpecialization(specialization);
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        if (specialization == null || specialization.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialization cannot be empty");
        }
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Doctor: " + super.toString() + ", specialization = " + specialization;
    }
}