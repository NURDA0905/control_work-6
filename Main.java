import com.sun.net.httpserver.HttpServer;
import handler.AddHandler;
import handler.CalendarHandler;
import handler.DeleteHandler;
import handler.ScheduleHandler;
import model.AppointmentManager;
import utils.TemplateEngine;

import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        var server = HttpServer.create(new InetSocketAddress(8989), 0);
        var engine = new TemplateEngine("templates");
        var manager = new AppointmentManager();

        server.createContext("/calendar", new CalendarHandler(engine, manager));
        server.createContext("/schedule", new ScheduleHandler(engine, manager));
        server.createContext("/add", new AddHandler(manager));
        server.createContext("/delete", new DeleteHandler(manager));

        server.setExecutor(null);
        server.start(); // ✅ СТАРТ ПОСЛЕ createContext
        System.out.println("Сервер запущен: http://localhost:8989/calendar");
    }
}
