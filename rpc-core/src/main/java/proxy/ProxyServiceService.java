package proxy;

import java.lang.reflect.Proxy;

public class ProxyServiceService {
    public static <T> T getProxy(Class<T> tClass){
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(),
                new Class[]{tClass},
                new ProxyService());
    }
}
