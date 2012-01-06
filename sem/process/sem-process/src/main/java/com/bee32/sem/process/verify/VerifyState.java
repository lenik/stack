package com.bee32.sem.process.verify;

import java.util.Collection;

import com.bee32.sem.event.EventFlags;
import com.bee32.sem.event.EventState;
import com.bee32.sem.event.EventStateClass;

public class VerifyState
        extends EventState<VerifyState> {

    private static final long serialVersionUID = 1L;

    static final int _CLASS = __class__(EventStateClass.VERIFY);

    public static final int INIT = 0;
    public static final int RUNNING = 1;
    public static final int END = 2;
    private final int stage;

    private final int eventFlags;

    public VerifyState(int index, String name, int stage, int eventFlags) {
        super(_CLASS | index, name);
        this.stage = stage;
        this.eventFlags = eventFlags;
    }

    public int getStage() {
        return stage;
    }

    public boolean isFinalized() {
        return stage == END;
    }

    public int getEventFlags() {
        return eventFlags;
    }

    public static VerifyState forName(String name) {
        return forName(VerifyState.class, name);
    }

    public static Collection<VerifyState> values() {
        return values(VerifyState.class);
    }

    public static VerifyState valueOf(Integer value) {
        return valueOf(VerifyState.class, value);
    }

    public static VerifyState valueOf(int value) {
        return valueOf(new Integer(value));
    }

    static VerifyState _(int index, String name, int stagefinalized, int eventFlags) {
        return new VerifyState(index, name, stagefinalized, eventFlags);
    }

    public static final VerifyState NOT_APPLICABLE = _(0, "NOT_APPLICABLE", INIT, EventFlags.WARNING);
    public static final VerifyState UNKNOWN = _(1, "UNKNOWN", INIT, 0);
    public static final VerifyState VERIFIED = _(2, "VERIFIED", END, EventFlags.NOTICE);
    public static final VerifyState REJECTED = _(3, "REJECTED", END, EventFlags.NOTICE);
    public static final VerifyState PENDING = _(4, "PENDING", RUNNING, 0);
    public static final VerifyState INVALID = _(5, "INVALID", INIT, EventFlags.ERROR);

}
