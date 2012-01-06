package com.bee32.sem.event;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.bee32.plover.arch.util.NoSuchEnumException;

public class GenericState
        extends EventState<GenericState> {

    private static final long serialVersionUID = 1L;

    static final int _CLASS = __class__(EventStateClass.GENERIC);
    static final Map<String, GenericState> nameMap = getNameMap(GenericState.class);
    static final Map<Integer, GenericState> valueMap = getValueMap(GenericState.class);

    public GenericState(int index, String name) {
        super(_CLASS | index, name);
    }

    public static Collection<GenericState> values() {
        Collection<GenericState> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static GenericState forName(String name) {
        GenericState genericState = nameMap.get(name);
        if (genericState == null)
            throw new NoSuchEnumException(GenericState.class, name);
        return genericState;
    }

    public static GenericState valueOf(int value) {
        GenericState genericState = valueMap.get(value);
        if (genericState == null)
            throw new NoSuchEnumException(GenericState.class, value);

        return genericState;
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
