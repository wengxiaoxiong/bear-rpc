package proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import model.RpcRequest;
import model.RpcResponse;
import rpc.RpcApplication;
import serializer.JdkSerializer;
import serializer.Serializer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyService implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        Serializer serializer = new JdkSerializer();

        RpcRequest build = RpcRequest
                .builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        byte[] serialize = serializer.serialize(build);

        try{
            byte[] result;
            HttpResponse httpResponse = HttpRequest.post(RpcApplication.getRpcConfig().getServerHost()).body(serialize).execute();
            result = httpResponse.bodyBytes();
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return rpcResponse.getData();
        }catch (Exception e ){
            e.printStackTrace();
        }

        return null;



    }
}
