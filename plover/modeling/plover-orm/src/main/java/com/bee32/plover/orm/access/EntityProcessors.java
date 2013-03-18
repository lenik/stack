package com.bee32.plover.orm.access;

import java.util.*;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.util.PriorityComparator;

public class EntityProcessors {

    Set<IEntityProcessor> processors;

    public EntityProcessors() {
        processors = new TreeSet<IEntityProcessor>(PriorityComparator.INSTANCE);
        for (IEntityProcessor processor : ServiceLoader.load(IEntityProcessor.class))
            processors.add(processor);
    }

    public Map<String, Object> getEventListeners() {
        Map<String, List<Object>> map = new LinkedHashMap<String, List<Object>>();
        for (IEntityProcessor processor : processors) {
            for (String eventName : processor.getInterestingEvents()) {
                Class<?> eventType = HibernateEvents.getEventType(eventName);
                if (eventType == null)
                    throw new IllegalUsageException("Illegal event type: " + eventName);
                if (!eventType.isInstance(processor))
                    throw new IllegalUsageException("Not a valid listener for event type: " + eventName);
                List<Object> listeners = map.get(eventName);
                if (listeners == null) {
                    listeners = new ArrayList<Object>(1);
                    map.put(eventName, listeners);
                }
                listeners.add(processor);
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
