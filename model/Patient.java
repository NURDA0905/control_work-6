package model;

import java.time.LocalDate;

public class Patient {
    private final String fullName;
    private final LocalDate birthDate;
    private final String type; // "первичный" или "вторичный"
    private final String symptoms;

    public Patient(String fullName, LocalDate birthDate, String type, String symptoms) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.type = type;
        this.symptoms = symptoms;
    }

    public String getFullName() { return fullName; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getType() { return type; }
    public String getSymptoms() { return symptoms; }
}