package handler;

import com.sun.net.httpserver.*;
import model.Appointment;
import model.AppointmentManager;
import model.Patient;

import java.io.*;
import java.net.URI;
import java.time.*;
import java.util.HashMap;
import java.util.Map;

public class AddHandler implements HttpHandler {
    private final AppointmentManager manager;

    public AddHandler(AppointmentManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        URI uri = exchange.getRequestURI();
        String query = uri.getQuery();
        String dateStr = query.split("date=")[1];
        LocalDate date = LocalDate.parse(dateStr);

        Map<String, String> form = parseForm(exchange.getRequestBody());
        String fullName = form.get("fullName");
        String birth = form.get("birthDate");
        String type = form.get("type");
        String timeStr = form.get("time");
        String symptoms = form.get("symptoms");

        Patient p = new Patient(fullName, LocalDate.parse(birth), type, symptoms);
        Appointment a = new Appointment(p, date, LocalTime.parse(timeStr));
        manager.add(a);

        String redirect = "/schedule?date=" + date;
        exchange.getResponseHeaders().add("Location", redirect);
        exchange.sendResponseHeaders(303, -1);
    }

    private Map<String, String> parseForm(InputStream body) throws IOException {
        String formData = new String(body.readAllBytes());
        Map<String, String> map = new HashMap<>();
        for (String pair : formData.split("&")) {
            String[] parts = pair.split("=");
            if (parts.length == 2) {
                map.put(parts[0], java.net.URLDecoder.decode(parts[1], "UTF-8"));
            }
        }
        return map;
    }
}
