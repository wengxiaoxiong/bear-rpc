package com.wengxiaoxiong;

import com.wengxiaoxiong.service.UserService;
import registry.LocalRegistry;
import server.VertxHttpServer;

public class ProviderExample {
    public static void main(String[] args) {
        VertxHttpServer vertxHttpServer = new VertxHttpServer();
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        vertxHttpServer.doStart(8080);

    }
}
