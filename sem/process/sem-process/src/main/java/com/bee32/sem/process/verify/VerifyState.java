package com.bee32.sem.process.verify;

import com.bee32.sem.event.EventFlags;
import com.bee32.sem.event.EventState;
import com.bee32.sem.event.EventStateClass;

public class VerifyState
        extends EventState {

    private static final long serialVersionUID = 1L;

    private final boolean closed;
    private final int eventFlags;

    private final String displayName;
    private final String description;

    public VerifyState(int id, String name, boolean closed, int eventFlags) {
        super(id, name);

        this.closed = closed;
        this.eventFlags = eventFlags;

        displayName = _nls(name + ".displayName", name);
        description = _nls(name + ".description", null);
    }

    public boolean isClosed() {
        return closed;
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

    static VerifyState _(int index, String name, boolean closed, int eventFlags) {
        return new VerifyState(VERIFY_STATE + index, name, closed, eventFlags);
    }

    public static final VerifyState NOT_APPLICABLE = _(0, "NOT_APPLICABLE", false, EventFlags.WARNING);
    public static final VerifyState UNKNOWN = _(1, "UNKNOWN", false, 0);
    public static final VerifyState VERIFIED = _(2, "VERIFIED", true, EventFlags.NOTICE);
    public static final VerifyState REJECTED = _(3, "REJECTED", true, EventFlags.NOTICE);
    public static final VerifyState PENDING = _(4, "PENDING", false, 0);
    public static final VerifyState INVALID = _(5, "INVALID", false, EventFlags.ERROR);

}
