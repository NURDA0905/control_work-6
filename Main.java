import com.sun.net.httpserver.HttpServer;
import handler.CalendarHandler;
import model.AppointmentManager;
import utils.TemplateEngine;


import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        var server = HttpServer.create(new InetSocketAddress(8989), 0);
        var engine = new TemplateEngine("templates");
        var manager = new AppointmentManager();

        server.createContext("/calendar", new CalendarHandler(engine, manager));
        server.setExecutor(null);
        server.start();
        System.out.println("Сервер запущен: http://localhost:8989/calendar");
    }
}
