import java.lang.classfile.instruction.ReturnInstruction;
import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private static int idGen=1;
    private int patientId;
    private int doctorId;
    private LocalDateTime appointmentTime;
    private String status;

    public Appointment (int patiendId, int doctorId, LocalDateTime appointmentTime,String status){
        id = idGen;
        idGen++;
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
        if (patientId<1){
            throw new IllegalArgumentException("Doctor's id cannot be less than 1");
        }
        this.doctorId = doctorId;
    }


    public int getPatientId(){
        return patientId;
    }

    public void setPatientId(int doctorId) {
        if (patientId<1){
            throw new IllegalArgumentException("Patient's id cannot be less than 1");
        }
        this.doctorId = doctorId;
    }



    public LocalDateTime getAppointment(){
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime){
        if (appointmentTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Appointment time cannot be in the past.");
        }
        this.appointmentTime=appointmentTime;// yyyy-MM-dd HH:mm
    }



    public String getStatus(){
        return status;
    }

    public void setStatus(String status) {
        if (status==null || status.isEmpty()){
            throw new IllegalArgumentException("Status cannot be empty");
        }
        this.status = status;
    }
    @Override public String toString() {
        return "Patient{id=" + getId() +  "Doctor{id=" + getDoctorId() + "appointmentTime{=" + getAppointment() + "status{=" + getStatus() ;
    }
}
