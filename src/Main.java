import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        // Створення HTTP сервера на порту 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Обробник для вашого головного шляху
        server.createContext("/", new MyHandler());

        server.createContext("/api/info", new InfoHandler());
        server.createContext("/api/users", new UsersHandler());

        server.setExecutor(null); // за замовчуванням використовується пул потоків
        server.start();
        System.out.println("Сервер запущено на порту 8000");
    }
}