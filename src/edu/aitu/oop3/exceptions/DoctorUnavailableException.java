//если врач не работает в выбранное время (выходной или время вне рабочего)
package edu.aitu.oop3.exceptions;

import java.time.LocalDateTime;

public class DoctorUnavailableException extends Exception {
    public DoctorUnavailableException(int doctorId, LocalDateTime time) {
        super("Doctor with ID " + doctorId + " is not available at " + time);
    }
}