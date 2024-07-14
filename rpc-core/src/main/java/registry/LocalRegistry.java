package registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalRegistry {
    private static final Map<String,Class<?>> map = new ConcurrentHashMap<>();

    public static void register(String name, Class<?> implClass){
        map.put(name,implClass);
    }

    public static Class<?> get(String name){
        return map.get(name);
    }

    public static void remove(String name){
        map.remove(name);
    }
}
