package com.bee32.sem.event;

public class GenericState
        extends EventState {

    public GenericState(int id, String name) {
        super(id, name);
    }

    static final int _CLASS = __class__(SEL_MASK, EventStateClass.ESC_EVENT);

    static GenericState _(int index, String name) {
        return new GenericState(_CLASS + index, name);
    }

    public static final GenericState UNKNOWN = _(0, "unknown");
    public static final GenericState RUNNING = _(1, "running");
    public static final GenericState SUSPENDED = _(2, "suspended");
    public static final GenericState CANCELED = _(3, "canceled");
    public static final GenericState DONE = _(4, "done");
    public static final GenericState FAILED = _(5, "failed");
    public static final GenericState ERRORED = _(6, "errored");

}
