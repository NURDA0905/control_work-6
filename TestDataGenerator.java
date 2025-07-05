import model.Appointment;
import model.AppointmentManager;
import model.Patient;

import java.time.LocalDate;
import java.time.LocalTime;

public class TestDataGenerator {
    public static void populate(AppointmentManager manager) {
        manager.add(new Appointment(
                new Patient("Иванов Иван", LocalDate.of(1990, 1, 1), "первичный", "температура"),
                LocalDate.now(),
                LocalTime.of(10, 0)
        ));

        manager.add(new Appointment(
                new Patient("Петров Петр", LocalDate.of(1985, 5, 12), "вторичный", "кашель"),
                LocalDate.now().plusDays(1),
                LocalTime.of(11, 30)
        ));
    }
}
