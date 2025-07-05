package model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppointmentManager {
    private final List<Appointment> appointments = new ArrayList<>();

    public void add(Appointment a) {
        appointments.add(a);
    }

    public Map<LocalDate, List<Appointment>> getMonthlySchedule(YearMonth month) {
        return appointments.stream()
                .filter(a -> YearMonth.from(a.getDate()).equals(month))
                .collect(Collectors.groupingBy(Appointment::getDate));
    }

    public List<Appointment> getByDate(LocalDate date) {
        return appointments.stream()
                .filter(a -> a.getDate().equals(date))
                .collect(Collectors.toList());
    }
    public void removeById(int id) {
        appointments.removeIf(a -> a.getId() == id);
    }

}
