package com.wengxiaoxiong;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.wengxiaoxiong.model.User;
import com.wengxiaoxiong.service.UserService;
import model.RpcRequest;
import model.RpcResponse;
import serializer.JdkSerializer;
import serializer.Serializer;

public class UserServiceProxy implements UserService {
    @Override
    public User getUser(User u) {
        Serializer serializer = new JdkSerializer();
        // 构造RPC请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{u})
                .build();
        try{
            byte[] serialize = serializer.serialize(rpcRequest);
            byte[] result;
            HttpResponse httpResponse = HttpRequest.post("http://localhost:8080").body(serialize).execute();
            result = httpResponse.bodyBytes();
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return (User) rpcResponse.getData();
        }catch (Exception e ){
            e.printStackTrace();
        }
        return null;
    }
}
