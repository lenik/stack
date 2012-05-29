package com.bee32.sem.process.verify;

import java.util.Collection;
import java.util.List;

import com.bee32.sem.event.EventFlags;
import com.bee32.sem.event.EventState;
import com.bee32.sem.event.EventStateClass;
import com.bee32.sem.process.verify.dto.IVerifiableDto;

public class VerifyEvalState
        extends EventState<VerifyEvalState> {

    private static final long serialVersionUID = 1L;

    static final int _CLASS = __class__(EventStateClass.VERIFY);

    public static final int INIT = 0;
    public static final int RUNNING = 1;
    public static final int END = 2;
    private final int type;

    private final int eventFlags;

    private VerifyEvalState(int index, String name, int stage, int eventFlags) {
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

    public static VerifyEvalState forValue(Integer value) {
        return forValue(VerifyEvalState.class, value);
    }

    public static VerifyEvalState forValue(int value) {
        return forValue(new Integer(value));
    }

    public VerifyEvalState meet(VerifyEvalState other) {
        return meet(this, other);
    }

    public static VerifyEvalState meet(VerifyEvalState s1, VerifyEvalState s2) {
        if (s1 == null)
            return s2;
        if (s2 == null)
            return s1;
        if (s1 == s2)
            return s1;

        if (s1 == NOT_APPLICABLE || s2 == NOT_APPLICABLE)
            return NOT_APPLICABLE;

        if (s1 == INVALID || s2 == INVALID)
            return INVALID;

        if (s1 == UNKNOWN || s2 == UNKNOWN)
            return UNKNOWN;

        if (s1 == PENDING || s2 == PENDING)
            return PENDING;

        return NOT_APPLICABLE;
    }

    public static VerifyEvalState meet(VerifyEvalState... states) {
        if (states.length == 0)
            return UNKNOWN;
        VerifyEvalState min = states[0];
        for (int i = 1; i < states.length; i++)
            min = meet(min, states[i]);
        return min;
    }

    public static VerifyEvalState meet(List<? extends IVerifiableDto> verifiables) {
        VerifyEvalState[] statev = new VerifyEvalState[verifiables.size()];
        int index = 0;
        for (IVerifiableDto verifiable : verifiables)
            statev[index++] = verifiable.getVerifyContext().getVerifyEvalState();
        VerifyEvalState state = meet(statev);
        return state;
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

    public static void main(String[] args) {
        System.out.println(VERIFIED.getValue());
    }
}
