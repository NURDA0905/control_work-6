package handler;

import com.sun.net.httpserver.*;
import model.AppointmentManager;

import java.io.IOException;
import java.net.URI;

public class DeleteHandler implements HttpHandler {
    private final AppointmentManager manager;

    public DeleteHandler(AppointmentManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String[] parts = uri.getQuery().split("&");

        int id = -1;
        String dateStr = null;

        for (String p : parts) {
            if (p.startsWith("id=")) id = Integer.parseInt(p.substring(3));
            else if (p.startsWith("date=")) dateStr = p.substring(5);
        }

        manager.removeById(id);
        exchange.getResponseHeaders().add("Location", "/schedule?date=" + dateStr);
        exchange.sendResponseHeaders(303, -1);
    }
}