package com.bee32.sem.event;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.NoSuchEnumException;

public class GenericState
        extends EventState {

    private static final long serialVersionUID = 1L;

    static final Map<String, GenericState> nameMap = new HashMap<String, GenericState>();
    static final Map<Integer, GenericState> valueMap = new HashMap<Integer, GenericState>();

    public GenericState(int id, String name) {
        super(id, name);

        nameMap.put(name, this);
        valueMap.put(id, this);
    }

    public static Collection<GenericState> values() {
        Collection<GenericState> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static GenericState forName(String altName) {
        GenericState genericState = nameMap.get(altName);
        if (genericState == null)
            throw new NoSuchEnumException(GenericState.class, altName);
        return genericState;
    }

    public static GenericState valueOf(Integer value) {
        if (value == null)
            return null;

        GenericState genericState = valueMap.get(value);
        if (genericState == null)
            throw new NoSuchEnumException(GenericState.class, value);

        return genericState;
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
