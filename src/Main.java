import edu.aitu.oop3.components.doctors.Doctor;
import edu.aitu.oop3.components.doctors.DoctorService;
import edu.aitu.oop3.infrastructure.persistence.*;
import edu.aitu.oop3.components.patientrecords.Patient;
import edu.aitu.oop3.components.scheduling.*;
import edu.aitu.oop3.components.doctors.IDoctorRepository;
import edu.aitu.oop3.components.patientrecords.IPatientRepository;
import edu.aitu.oop3.factories.UserFactory;
import edu.aitu.oop3.shared.IUser;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class  Main {
    public static void main(String[] args) {
        IDB db = PostgresDB.getInstance();
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
            DoctorService doctorService = new DoctorService(doctorRepo);
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
                System.out.println("9. Search Doctor by Specialization");
                System.out.println("10. View Clinic Statistics (New Feature)");
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
                            IUser newUser = UserFactory.createUser("PATIENT", 0, pName, pPhone);
                            if (newUser instanceof Patient) {
                                Patient patientToSave = (Patient) newUser;
                                patientToSave.setEmail(pEmail);
                                patientRepo.add(patientToSave);
                                System.out.println("Success! Patient registered. ID: " + patientToSave.getId());
                            }
                            break;


                        case 2:
                            System.out.print("Doctor Name: "); String dName = scanner.nextLine();
                            System.out.print("Specialization: "); String dSpec = scanner.nextLine();
                            IUser newDoc = UserFactory.createUser("DOCTOR", 0, dName, dSpec);
                            if (newDoc instanceof Doctor) {
                                doctorRepo.add((Doctor) newDoc);
                                System.out.println("Success! Doctor registered. ID: " + newDoc.getId());
                            }
                            break;

                        case 3:
                            System.out.print("Patient ID: "); int pId = scanner.nextInt();
                            System.out.print("Doctor ID: "); int dId = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Time (yyyy-MM-dd HH:mm): ");
                            LocalDateTime time = LocalDateTime.parse(scanner.nextLine(), formatter);

                            Appointment newApp = new Appointment.Builder()
                                    .setPatientId(pId)
                                    .setDoctorId(dId)
                                    .setAppointmentTime(time)
                                    .setStatus("scheduled")
                                    .build();

                            appointmentService.bookAppointment(newApp);

                            System.out.println("Appointment booked!");
                            System.out.println("[NotificationComponent] SMS sent to Patient ID: " + pId);
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
                        case 9:
                            System.out.print("Enter specialization (e.g. Dentist): ");
                            String spec = scanner.nextLine().trim();
                            List<Doctor> filteredDoctors = doctorService.getDoctorsSortedBySpecialization(spec);

                            if (filteredDoctors.isEmpty()) {
                                System.out.println("No doctors found with this specialization.");
                            } else {
                                System.out.println("Found & Sorted Doctors");
                                filteredDoctors.forEach(doc -> System.out.println(doc.getFullName() + " | " + doc.getSpecialization()));
                            }
                            break;
                        case 10:
                            System.out.println("\nClinic Statistics");
                            long activeCount = appointmentService.getActiveAppointmentsCount();

                            System.out.println("Current active appointments in system: " + activeCount);
                            System.out.println("Total patients registered: " + patientRepo.findAll().size());
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