package utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.util.*;

public class TemplateEngine {
    private final Configuration cfg;

    public TemplateEngine(String templateDir) throws IOException {
        cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setDirectoryForTemplateLoading(new File("templates"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
    }

    public void render(HttpExchange exchange, String templateName, Map<String, Object> dataModel) throws IOException {
        try {
            Template template = cfg.getTemplate(templateName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (Writer out = new OutputStreamWriter(baos)) {
                template.process(dataModel, out);
            }
            byte[] response = baos.toByteArray();
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.getResponseBody().close();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
