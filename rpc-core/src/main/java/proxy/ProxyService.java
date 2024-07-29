package proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import config.RpcConfig;
import model.RpcRequest;
import model.RpcResponse;
import rpc.RpcApplication;
import serializer.JdkSerializer;
import serializer.Serializer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ServiceLoader;

public class ProxyService implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


//        Serializer serializer = new JdkSerializer();

        Serializer serializer = null;
        ServiceLoader<Serializer> serviceLoader = ServiceLoader.load(Serializer.class);
        for(Serializer service: serviceLoader){
            serializer =service;
        }

        RpcRequest build = RpcRequest
                .builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        byte[] serialize = serializer.serialize(build);

        try{

            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            byte[] result;
            HttpResponse httpResponse = HttpRequest.post("http://"+rpcConfig.getServerHost()+":"+rpcConfig.getServerPort()+"/").body(serialize).execute();
            result = httpResponse.bodyBytes();
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return rpcResponse.getData();
        }catch (Exception e ){
            e.printStackTrace();
        }

        return null;



    }
}
