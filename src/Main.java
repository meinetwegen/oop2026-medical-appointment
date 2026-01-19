import edu.aitu.oop3.db.IDB;
import edu.aitu.oop3.db.PostgresDB;
import edu.aitu.oop3.models.*;
import edu.aitu.oop3.repositories.*;
import edu.aitu.oop3.repositories.interfaces.IAppointmentRepository;
import edu.aitu.oop3.repositories.interfaces.IDoctorRepository;
import edu.aitu.oop3.repositories.interfaces.IPatientRepository;
import edu.aitu.oop3.services.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        IDB db = new PostgresDB();
        System.out.println("Connecting to Supabase...");
        try (Connection connection = db.getConnection()) {
            System.out.println("Connected successfully!");
            String sqlCheck = "SELECT CURRENT_TIMESTAMP";
            try (PreparedStatement stmt = connection.prepareStatement(sqlCheck);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Database time: " + rs.getTimestamp(1));
                }
            }

            IAppointmentRepository appRepo = new PostgresAppointmentRepository(db);
            IDoctorRepository doctorRepo = new PostgresDoctorRepository(db);
            IPatientRepository patientRepo = new PostgresPatientRepository(db);

            DoctorAvailabilityService availabilityService = new DoctorAvailabilityService(appRepo);
            AppointmentService appointmentService = new AppointmentService(appRepo, doctorRepo, patientRepo, availabilityService);

            Scanner scanner = new Scanner(System.in);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            while (true) {
                System.out.println("\n--- Medical Appointment System ---");
                System.out.println("1. Register Patient (Add to DB)");
                System.out.println("2. Register Doctor (Add to DB)");
                System.out.println("3. Book Appointment");
                System.out.println("4. Cancel Appointment");
                System.out.println("5. View Doctor's Schedule");
                System.out.println("6. View Patient's Upcoming Visits");
                System.out.println("7. Show All Patients");
                System.out.println("8. Show All Doctors");
                System.out.println("0. Exit");
                System.out.print("Select an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 0) break;

                try {
                    switch (choice) {
                        case 1:
                            System.out.print("Full Name: "); String pName = scanner.nextLine();
                            System.out.print("Email: "); String pEmail = scanner.nextLine();
                            System.out.print("Phone: "); String pPhone = scanner.nextLine();
                            Patient p = new Patient(pName, pEmail, pPhone);
                            patientRepo.add(p);
                            System.out.println("Success! Patient registered. Assigned ID: " + p.getId());
                            break;

                        case 2:
                            System.out.print("Doctor Name: "); String dName = scanner.nextLine();
                            System.out.print("Specialization: "); String dSpec = scanner.nextLine();
                            Doctor d = new Doctor(dName, dSpec);
                            doctorRepo.add(d);
                            System.out.println("Success! Doctor registered. Assigned ID: " + d.getId());
                            break;

                        case 3:
                            System.out.print("Patient ID: "); int pId = scanner.nextInt();
                            System.out.print("Doctor ID: "); int dId = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Time (yyyy-MM-dd HH:mm): ");
                            LocalDateTime time = LocalDateTime.parse(scanner.nextLine(), formatter);
                            appointmentService.bookAppointment(new Appointment(pId, dId, time, "scheduled"));
                            System.out.println("Appointment booked!");
                            break;

                        case 4:
                            System.out.print("Appointment ID: ");
                            appointmentService.cancelAppointment(scanner.nextInt());
                            System.out.println("Cancelled.");
                            break;

                        case 5:
                            System.out.print("Enter Doctor ID: ");
                            int searchDocId = scanner.nextInt();
                            List<Appointment> docSchedule = appointmentService.getDoctorSchedule(searchDocId);

                            if (docSchedule.isEmpty()) {
                                System.out.println("No appointments found for Doctor ID " + searchDocId + ". The schedule is empty.");
                            } else {
                                System.out.println("Schedule for Doctor " + searchDocId + ":");
                                docSchedule.forEach(System.out::println);
                            }
                            break;

                        case 6:
                            System.out.print("Enter Patient ID: ");
                            int searchPatId = scanner.nextInt();
                            List<Appointment> patientVisits = appRepo.findByPatientId(searchPatId);

                            if (patientVisits.isEmpty()) {
                                System.out.println("No upcoming visits found for Patient ID " + searchPatId + ".");
                            } else {
                                System.out.println("Visits for Patient " + searchPatId + ":");
                                patientVisits.forEach(System.out::println);
                            }
                            break;
                        case 7:
                            List<Patient> allPatients = patientRepo.findAll();
                            if (allPatients.isEmpty()) {
                                System.out.println("No patients registered yet.");
                            } else {
                                System.out.println("--- Registered Patients ---");
                                allPatients.forEach(System.out::println);
                            }
                            break;

                        case 8:
                            List<Doctor> allDoctors = doctorRepo.findAll();
                            if (allDoctors.isEmpty()) {
                                System.out.println("No doctors registered yet.");
                            } else {
                                System.out.println("--- Registered Doctors ---");
                                allDoctors.forEach(System.out::println);
                            }
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("--- DEBUG ERROR INFO ---");
                    e.printStackTrace();
                    System.out.println("------------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
}