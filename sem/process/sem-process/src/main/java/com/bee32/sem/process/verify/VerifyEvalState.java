package com.bee32.sem.process.verify;

import java.util.Collection;

import com.bee32.sem.event.EventFlags;
import com.bee32.sem.event.EventState;
import com.bee32.sem.event.EventStateClass;

public class VerifyEvalState
        extends EventState<VerifyEvalState> {

    private static final long serialVersionUID = 1L;

    static final int _CLASS = __class__(EventStateClass.VERIFY);

    public static final int INIT = 0;
    public static final int RUNNING = 1;
    public static final int END = 2;
    private final int type;

    private final int eventFlags;

    public VerifyEvalState(int index, String name, int stage, int eventFlags) {
        super(_CLASS | index, name);
        this.type = stage;
        this.eventFlags = eventFlags;
    }

    public int getType() {
        return type;
    }

    public boolean isFinalized() {
        return type == END;
    }

    public int getEventFlags() {
        return eventFlags;
    }

    public static VerifyEvalState forName(String name) {
        return forName(VerifyEvalState.class, name);
    }

    public static Collection<VerifyEvalState> values() {
        return values(VerifyEvalState.class);
    }

    public static VerifyEvalState valueOf(Integer value) {
        return valueOf(VerifyEvalState.class, value);
    }

    public static VerifyEvalState valueOf(int value) {
        return valueOf(new Integer(value));
    }

    static VerifyEvalState _(int index, String name, int stagefinalized, int eventFlags) {
        return new VerifyEvalState(index, name, stagefinalized, eventFlags);
    }

    public static final VerifyEvalState NOT_APPLICABLE = _(0, "NOT_APPLICABLE", INIT, EventFlags.WARNING);
    public static final VerifyEvalState UNKNOWN = _(1, "UNKNOWN", INIT, 0);
    public static final VerifyEvalState VERIFIED = _(2, "VERIFIED", END, EventFlags.NOTICE);
    public static final VerifyEvalState REJECTED = _(3, "REJECTED", END, EventFlags.NOTICE);
    public static final VerifyEvalState PENDING = _(4, "PENDING", RUNNING, 0);
    public static final VerifyEvalState INVALID = _(5, "INVALID", END, EventFlags.ERROR);

}
