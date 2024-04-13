import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws Exception {
        // Створення HTTP сервера на порту 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Обробник для вашого головного шляху
        server.createContext("/", new MyHandler());

        server.setExecutor(null); // за замовчуванням використовується пул потоків
        server.start();
        System.out.println("Сервер запущено на порту 8000");
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Отримання запиту методом GET
            if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                // Отримання шляху запиту
                String path = exchange.getRequestURI().getPath();

                // Обробка статичних файлів
                if (path.equals("/") || path.equals("/index.html")) {
                    path = "/index.html"; // Встановлюємо ім'я статичного файлу, якщо корінний шлях або /index.html
                }

                File file = new File("src" + path);
                if (file.exists()) {
                    // Читання файлу та відправлення відповіді
                    byte[] bytes = Files.readAllBytes(file.toPath());
                    exchange.sendResponseHeaders(200, bytes.length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(bytes);
                    os.close();
                } else {
                    // Відповідь 404, якщо файл не знайдено
                    exchange.sendResponseHeaders(404, 0);
                }
            } else {
                // Відповідь на непідтриманий метод запиту
                exchange.sendResponseHeaders(405, 0);
            }
        }
    }
}