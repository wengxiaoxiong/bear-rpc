package com.wengxiaoxiong;

import com.wengxiaoxiong.service.UserService;
import config.RpcConfig;
import registry.LocalRegistry;
import rpc.RpcApplication;
import server.HttpServer;
import server.VertxHttpServer;

public class ProviderExample {
    public static void main(String[] args) {


        // 初始化RPC框架
        RpcApplication.init();

        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动web服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());

    }
}
