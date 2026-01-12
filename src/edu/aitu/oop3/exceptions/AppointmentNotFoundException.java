//при попытке отмены или поиска несуществующего id записи
package edu.aitu.oop3.exceptions;

public class AppointmentNotFoundException extends Exception {
    private final int appointmentId;

    public AppointmentNotFoundException(int id) {
        super("Appointment with ID " + id + " was not found in the system.");
        this.appointmentId = id;
    }
}