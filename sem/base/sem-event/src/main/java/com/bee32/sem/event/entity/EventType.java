package com.bee32.sem.event.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.NoSuchEnumException;

public class EventType
        extends EnumAlt<Character, EventType> {

    private static final long serialVersionUID = 1L;

    public EventType(Character value, String name) {
        super(value, name);
    }

    static final Map<String, EventType> nameMap = new HashMap<String, EventType>();
    static final Map<Character, EventType> valueMap = new HashMap<Character, EventType>();

    @Override
    protected Map<String, EventType> getNameMap() {
        return nameMap;
    }

    @Override
    protected Map<Character, EventType> getValueMap() {
        return valueMap;
    }

    public static Collection<EventType> values() {
        Collection<EventType> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static EventType valueOf(char value) {
        EventType eventType = valueMap.get(value);
        if (eventType == null)
            throw new NoSuchEnumException(EventType.class, value);

        return eventType;
    }

    public static EventType valueOf(String altName) {
        EventType eventType = nameMap.get(altName);
        if (eventType == null)
            throw new NoSuchEnumException(EventType.class, altName);
        return eventType;
    }

    public static final EventType EVENT = new EventType('e', "event");
    public static final EventType TASK = new EventType('t', "task");
    public static final EventType ACTIVITY = new EventType('a', "activity");

}
