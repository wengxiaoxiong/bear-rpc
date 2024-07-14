import com.wengxiaoxiong.service.UserService;
import registry.LocalRegistry;
import server.HttpServer;
import server.VertxHttpServer;

public class Main {
    public static void main(String[] args) {

        LocalRegistry.register(UserService.class.getName(),UserService.class);
        HttpServer server = new VertxHttpServer();
        server.doStart(8080);

    }
}
