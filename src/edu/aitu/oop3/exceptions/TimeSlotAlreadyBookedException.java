//пациент пытается записаться к врачу на время, которое уже занято
package edu.aitu.oop3.exceptions;

import java.time.LocalDateTime;

public class TimeSlotAlreadyBookedException extends Exception {
    public TimeSlotAlreadyBookedException(LocalDateTime time) {
        super("The time slot " + time + " is already booked by another patient.");
    }
}
