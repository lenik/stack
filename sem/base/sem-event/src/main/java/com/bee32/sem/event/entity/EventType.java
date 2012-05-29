package com.bee32.sem.event.entity;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;

public class EventType
        extends EnumAlt<Character, EventType> {

    private static final long serialVersionUID = 1L;

    public EventType(Character value, String name) {
        super(value, name);
    }

    public static EventType forName(String name) {
        return forName(EventType.class, name);
    }

    public static Collection<EventType> values() {
        return values(EventType.class);
    }

    public static EventType forValue(Character value) {
        return forValue(EventType.class, value);
    }

    public static EventType forValue(char value) {
        return forValue(new Character(value));
    }

    public static final EventType EVENT = new EventType('e', "event");
    public static final EventType TASK = new EventType('t', "task");
    public static final EventType ACTIVITY = new EventType('a', "activity");

}
