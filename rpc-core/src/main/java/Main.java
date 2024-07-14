public class Main {
    public static void main(String[] args) {
        HttpServer server = new VertxHttpServer();
        server.doStart(8080);

    }
}
