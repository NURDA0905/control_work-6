package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Appointment;
import model.AppointmentManager;
import utils.TemplateEngine;
import java.util.List;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CalendarHandler implements HttpHandler {
    private final TemplateEngine engine;
    private final AppointmentManager manager;

    public CalendarHandler(TemplateEngine engine, AppointmentManager manager) {
        this.engine = engine;
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        YearMonth month = YearMonth.now();
        Map<LocalDate, List<Appointment>> schedule = manager.getMonthlySchedule(month);

        List<List<Map<String, Object>>> calendar = new ArrayList<>();
        LocalDate firstDay = month.atDay(1);
        int firstDayOfWeek = firstDay.getDayOfWeek().getValue(); // 1=Monday
        LocalDate current = firstDay.minusDays(firstDayOfWeek - 1);

        for (int week = 0; week < 6; week++) {
            List<Map<String, Object>> weekRow = new ArrayList<>();
            for (int d = 0; d < 7; d++) {
                Map<String, Object> cell = new HashMap<>();
                if (current.getMonth().equals(month.getMonth())) {
                    cell.put("date", current.toString());
                    cell.put("patients", schedule.getOrDefault(current, List.of()));
                }
                cell.put("isToday", current.equals(LocalDate.now()));
                weekRow.add(cell);
                current = current.plusDays(1);
            }
            calendar.add(weekRow);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("calendar", calendar);
        data.put("month", month.getMonth().name() + " " + month.getYear());

        engine.render(exchange, "templates/calendar.ftl", data);
    }
}
