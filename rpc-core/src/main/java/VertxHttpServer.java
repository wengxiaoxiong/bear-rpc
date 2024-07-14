import io.vertx.core.Vertx;

public class VertxHttpServer implements HttpServer{
    @Override
    public void doStart(int portNumber) {
        Vertx vertx = Vertx.vertx();
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();


        server.requestHandler(request -> {
            System.out.println("receive request");
            request.response().putHeader("content-type","text/plain").end("hahha");
        });

        server.listen(portNumber,httpServerAsyncResult -> {
            if(httpServerAsyncResult.succeeded()){
                System.out.println("server is now running on port : "+portNumber);
            }else{
                System.err.println(httpServerAsyncResult.cause());
            }
        });
    }
}
