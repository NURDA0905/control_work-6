package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private final Patient patient;
    private final LocalDate date;
    private final LocalTime time;

    public Appointment(Patient patient, LocalDate date, LocalTime time) {
        this.patient = patient;
        this.date = date;
        this.time = time;
    }

    public Patient getPatient() { return patient; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }

    private static int idCounter = 0;
    private final int id = ++idCounter;

    public int getId() { return id; }

}
