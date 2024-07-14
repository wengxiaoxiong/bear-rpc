package server;

import com.wengxiaoxiong.model.User;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import model.RpcRequest;
import model.RpcResponse;
import registry.LocalRegistry;
import serializer.JdkSerializer;
import serializer.Serializer;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class HttpServerHandler implements Handler<HttpServerRequest> {


    // 1. 从HttpRequest中的参数反序列化为RpcRequest对象
    // 2. 获取RpcRequest中的 请求类型 请求参数 服务名 方法名
    // 3. 调用方法 获取返回值 封装RpcResponse
    // 4. 序列化RpcResponse 并且HttpResponse
    @Override
    public void handle(HttpServerRequest request) {

        // 指定序列化器
        final Serializer serializer = new JdkSerializer();

        // 记录请求
        System.out.println("Receive Request" + request.method() + " " + request.uri());




        // 处理http请求
        request.bodyHandler(body->{
            RpcRequest rpcRequest = null;
            RpcResponse rpcResponse = new RpcResponse();


            byte[] bytes = body.getBytes();


            // 反序列化rpcRequest
            try {
                rpcRequest = serializer.deserialize(bytes, RpcRequest.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(rpcRequest==null){
                rpcResponse.setMessage("请求为空");
                doResponse(request, rpcResponse, serializer);
            }

            // 调用
            try {
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());

                Object result = method.invoke(implClass.newInstance(), rpcRequest.getArgs());
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("OK");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setException(e);
                rpcResponse.setMessage(e.getMessage());
            }

            doResponse(request, rpcResponse, serializer);


        });


    }

    private void doResponse(HttpServerRequest request, RpcResponse rpcResponse, Serializer serializer) {

        HttpServerResponse httpServerResponse = request.response().putHeader("content-type", "application/json");

        try {
            byte[] serialized = serializer.serialize(rpcResponse);
            httpServerResponse.end(Buffer.buffer(serialized));
        } catch (IOException e) {
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }

    }


}
