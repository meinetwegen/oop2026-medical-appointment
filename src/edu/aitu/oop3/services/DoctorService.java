 package edu.aitu.oop3.services;

import edu.aitu.oop3.models.Doctor;
import edu.aitu.oop3.repositories.IDoctorRepository;
import java.sql.SQLException;
import java.util.List;

public class DoctorService {
    private final IDoctorRepository doctorRepo;

    public DoctorService(IDoctorRepository doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    public void findDoctorsBySpecialization(String spec) {
        try {
            List<Doctor> doctors = doctorRepo.findBySpecialization(spec);

            if (doctors.isEmpty()) {
                System.out.println("Doctors with specialization '" + spec + "' not found.");
            } else {
                System.out.println("Found doctors (" + spec + "):");
                for (Doctor doctor : doctors) {
                    System.out.println(doctor);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}