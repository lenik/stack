package com.bee32.sem.process.verify;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.NoSuchEnumException;
import com.bee32.sem.event.EventFlags;
import com.bee32.sem.event.EventState;
import com.bee32.sem.event.EventStateClass;

public class VerifyState
        extends EventState {

    private static final long serialVersionUID = 1L;

    static final Map<String, VerifyState> nameMap = new HashMap<String, VerifyState>();
    static final Map<Integer, VerifyState> valueMap = new HashMap<Integer, VerifyState>();

    private final boolean finalized;
    private final int eventFlags;

    private final String displayName;
    private final String description;

    public VerifyState(int id, String name, boolean finalized, int eventFlags) {
        super(id, name);

        this.finalized = finalized;
        this.eventFlags = eventFlags;

        displayName = _nls(name + ".label", name);
        description = _nls(name + ".description", null);

        nameMap.put(name, this);
        valueMap.put(id, this);
    }

    public static Collection<VerifyState> values() {
        Collection<VerifyState> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static VerifyState forName(String altName) {
        VerifyState verifyState = nameMap.get(altName);
        if (verifyState == null)
            throw new NoSuchEnumException(VerifyState.class, altName);
        return verifyState;
    }

    public static VerifyState valueOf(Integer value) {
        if (value == null)
            return null;

        VerifyState verifyState = valueMap.get(value);
        if (verifyState == null)
            throw new NoSuchEnumException(VerifyState.class, value);

        return verifyState;
    }

    public boolean isFinalized() {
        return finalized;
    }

    public int getEventFlags() {
        return eventFlags;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    static final int VERIFY_STATE = __class__(SEL_TASK, EventStateClass.ESC_PROCESS);

    static VerifyState _(int index, String name, boolean finalized, int eventFlags) {
        return new VerifyState(VERIFY_STATE + index, name, finalized, eventFlags);
    }

    public static final VerifyState NOT_APPLICABLE = _(0, "NOT_APPLICABLE", false, EventFlags.WARNING);
    public static final VerifyState UNKNOWN = _(1, "UNKNOWN", false, 0);
    public static final VerifyState VERIFIED = _(2, "VERIFIED", true, EventFlags.NOTICE);
    public static final VerifyState REJECTED = _(3, "REJECTED", true, EventFlags.NOTICE);
    public static final VerifyState PENDING = _(4, "PENDING", false, 0);
    public static final VerifyState INVALID = _(5, "INVALID", false, EventFlags.ERROR);

}
