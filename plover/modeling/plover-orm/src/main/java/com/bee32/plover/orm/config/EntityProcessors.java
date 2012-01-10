package com.bee32.plover.orm.config;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;

import com.bee32.plover.arch.util.PriorityComparator;

public class EntityProcessors {

    Set<IEntityProcessor> processors;

    public EntityProcessors() {
        processors = new TreeSet<IEntityProcessor>(PriorityComparator.INSTANCE);
        for (IEntityProcessor processor : ServiceLoader.load(IEntityProcessor.class))
            processors.add(processor);
    }

    static Set<Class<?>> getInterfaces(Class<?> clazz) {
        Set<Class<?>> set = new HashSet<Class<?>>();
        findInterfaces(clazz, set);
        return set;
    }

    static void findInterfaces(Class<?> clazz, Set<Class<?>> set) {
        for (Class<?> iface : clazz.getInterfaces())
            findInterfaces(iface, set);
        if (clazz.isInterface())
            set.add(clazz);
        else {
            Class<?> superclass = clazz.getSuperclass();
            if (superclass != null)
                findInterfaces(superclass, set);
        }
    }

    public Map<String, Object> getEventListeners() {
        Map<String, Object[]> map = new LinkedHashMap<String, Object[]>();
        for (IEntityProcessor processor : processors) {
            for (Object eventListener : processor.getEventListeners()) {
                for (Class<?> eventType : getInterfaces(eventListener.getClass())) {
                    String eventName = HibernateEvents.getEventName(eventType);
                    if (eventName == null)
                        continue;
                    Object[] listeners = map.get(eventName);
                    if (listeners == null) {
                        listeners = new Object[] { eventListener };
                    } else {
                        Object[] old = listeners;
                        listeners = new Object[old.length + 1];
                        System.arraycopy(old, 0, listeners, 0, old.length);
                        listeners[old.length] = eventListener;
                    }
                    map.put(eventName, listeners);
                }
            }
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> _map = (Map<String, Object>) (Object) map;
        return _map;
    }

    static EntityProcessors instance;

    public static EntityProcessors getInstance() {
        if (instance == null) {
            synchronized (EntityProcessors.class) {
                if (instance == null) {
                    instance = new EntityProcessors();
                }
            }
        }
        return instance;
    }

}
