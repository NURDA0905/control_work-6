package handler;

import com.sun.net.httpserver.*;
import model.Appointment;
import model.AppointmentManager;
import utils.TemplateEngine;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;

public class ScheduleHandler implements HttpHandler {
    private final TemplateEngine engine;
    private final AppointmentManager manager;

    public ScheduleHandler(TemplateEngine engine, AppointmentManager manager) {
        this.engine = engine;
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String query = uri.getQuery();
        if (query == null || !query.contains("date=")) {
            engine.render(exchange, "error.ftl", Map.of("message", "Дата не указана"));
            return;
        }

        String dateStr = query.split("date=")[1];
        LocalDate date = LocalDate.parse(dateStr);
        List<Appointment> appointments = manager.getByDate(date);
        appointments.sort(Comparator.comparing(Appointment::getTime));

        List<Map<String, Object>> patientList = new ArrayList<>();
        for (Appointment a : appointments) {
            Map<String, Object> p = new HashMap<>();
            p.put("id", a.getId());
            p.put("fullName", a.getPatient().getFullName());
            p.put("type", a.getPatient().getType());
            p.put("symptoms", a.getPatient().getSymptoms());
            p.put("time", a.getTime().toString());
            patientList.add(p);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("patients", patientList);
        data.put("date", date.toString());

        engine.render(exchange, "schedule.ftl", data);
    }
}
