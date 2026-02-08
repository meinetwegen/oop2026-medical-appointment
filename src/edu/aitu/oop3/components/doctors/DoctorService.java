 package edu.aitu.oop3.components.doctors;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

 public class DoctorService {
    private final IDoctorRepository doctorRepo;

    public DoctorService(IDoctorRepository doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

     public List<Doctor> getDoctorsSortedBySpecialization(String spec) throws SQLException{
         List<Doctor> allDoctors = doctorRepo.findAll();

         return allDoctors.stream()
                 .filter(d -> d.getSpecialization().equalsIgnoreCase(spec))
                 .sorted((d1, d2) -> d1.getFullName().compareTo(d2.getFullName()))
                 .collect(Collectors.toList());
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