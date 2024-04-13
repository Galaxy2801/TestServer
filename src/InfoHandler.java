import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

// Обробник для API /api/info
public class InfoHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Створення рядка JSON-відповіді вручну
        String json = "{\"сервер\": \"Java HTTP Server\", \"порт\": \"8000\", \"час запуску\": " +
                System.currentTimeMillis() + "}";

        // Надсилання JSON-відповіді
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, json.length());
        OutputStream os = exchange.getResponseBody();
        os.write(json.getBytes());
        os.close();
    }
}
