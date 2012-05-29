package com.bee32.sem.event;

import java.util.Collection;

public class GenericState
        extends EventState<GenericState> {

    private static final long serialVersionUID = 1L;

    static final int _CLASS = __class__(EventStateClass.GENERIC);

    public GenericState(int index, String name) {
        super(_CLASS | index, name);
    }

    public static GenericState forName(String name) {
        return forName(GenericState.class, name);
    }

    public static Collection<GenericState> values() {
        return values(GenericState.class);
    }

    public static GenericState forValue(Integer value) {
        return forValue(GenericState.class, value);
    }

    public static GenericState forValue(int value) {
        return forValue(new Integer(value));
    }

    static GenericState _(int index, String name) {
        return new GenericState(index, name);
    }

    public static final GenericState UNKNOWN = _(0, "unknown");
    public static final GenericState RUNNING = _(1, "running");
    public static final GenericState SUSPENDED = _(2, "suspended");
    public static final GenericState CANCELED = _(3, "canceled");
    public static final GenericState DONE = _(4, "done");
    public static final GenericState FAILED = _(5, "failed");
    public static final GenericState ERRORED = _(6, "errored");

}
