package com.bee32.sem.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.util.EnumAlt;

/**
 * Event state.
 */
public abstract class EventState<$ extends EventState<$>>
        extends EnumAlt<Integer, $> {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    static Map<String, EventState<?>> nameMap = getNameMap(EventState.class);
    @SuppressWarnings("unchecked")
    static Map<Integer, EventState<?>> valueMap = getValueMap(EventState.class);

    public static final int CLASS_MASK = 0xff000000;
    public static final int CLASS_SHIFT = 24;

    public static final int INDEX_MASK = 0x0000ffff;
    public static final int INDEX_SHIFT = 0;

    private final int index;

    protected EventState(int id, String name) {
        super(id, name);
        index = (id & INDEX_MASK) >> INDEX_SHIFT;
    }

    public int getIndex() {
        return index;
    }

    protected static int __class__(int classIndex) {
        return ((classIndex << CLASS_SHIFT) & CLASS_MASK);
    }

    public static Collection<? extends EventState<?>> values() {
        Collection<? extends EventState<?>> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static EventState<?> forName(String altName) {
        EventState<?> definedState = nameMap.get(altName);
        if (definedState != null)
            return definedState;
        throw new IllegalUsageException("Invalid state: " + altName);
    }

    public static EventState<?> valueOf(int altId) {
        EventState<?> definedState = valueMap.get(altId);
        if (definedState != null)
            return definedState;

        String idHex = Integer.toHexString(altId);
        throw new IllegalUsageException("Invalid state: 0x" + idHex);
    }

    public static List<Integer> list(int mask) {
        return list(mask, mask);
    }

    public static List<Integer> list(int mask, int test) {
        List<Integer> list = new ArrayList<Integer>();
        for (EventState<?> state : valueMap.values()) {
            if ((state.value & mask) == test)
                list.add(state.value);
        }
        return list;
    }

    public static List<Integer> listFor(int classIndex) {
        int classBits = classIndex << CLASS_SHIFT;

        return list(CLASS_MASK, classBits);
    }

}
