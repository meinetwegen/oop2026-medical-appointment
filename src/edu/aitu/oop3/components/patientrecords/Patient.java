package edu.aitu.oop3.components.patientrecords;

import edu.aitu.oop3.shared.User;

public class Patient extends User {
    private String email;
    private String phoneNumber;

    public Patient(String fullName, String email, String phoneNumber){
        super(fullName);
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }

    public Patient(int id, String fullName, String email, String phoneNumber) {
        super(id, fullName);
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Patient: " + super.toString() + ", email = " + email + ", phone = " + phoneNumber;
    }
}