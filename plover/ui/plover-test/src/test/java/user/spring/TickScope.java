package user.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class TickScope
        implements Scope {

    private final long start;
    private final int timeout;

    private int cacheVersion;
    private Map<String, Object> cache;

    public TickScope(int ticks) {
        this.start = System.currentTimeMillis();
        this.timeout = ticks * 1000;
        this.cache = new HashMap<String, Object>();
    }

    @Override
    public synchronized Object get(String name, ObjectFactory<?> objectFactory) {
        int current = (int) (System.currentTimeMillis() - start);

        if (current - cacheVersion >= timeout) {
            cache.clear();
            cacheVersion = current;
        }

        Object cached = cache.get(name);
        if (cached == null) {
            cached = objectFactory.getObject();
            cache.put(name, cached);
        }

        return cached;
    }

    @Override
    public Object remove(String name) {
        return cache.remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return "Tick" + timeout + "-" + cacheVersion;
    }

}
