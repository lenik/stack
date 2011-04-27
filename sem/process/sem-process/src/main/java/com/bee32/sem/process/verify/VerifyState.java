package com.bee32.sem.process.verify;

import java.util.ResourceBundle;

import com.bee32.sem.event.EventFlags;

public enum VerifyState {

    NOT_APPLICABLE(false, EventFlags.WARNING),

    UNKNOWN(false),

    VERIFIED(true, EventFlags.NOTICE),

    REJECTED(true, EventFlags.NOTICE),

    PENDING(false),

    INVALID(false, EventFlags.ERROR),

    ;

    private final boolean closed;
    private final int eventFlags;

    private final String displayName;
    private final String description;

    VerifyState(boolean closed) {
        this(closed, 0);
    }

    VerifyState(boolean closed, int eventFlags) {
        this.closed = closed;
        this.eventFlags = eventFlags;

        displayName = _Helper.get(name() + ".displayName", name());
        description = _Helper.get(name() + ".description");
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

    static class _Helper {

        public static ResourceBundle rb = ResourceBundle.getBundle(VerifyState.class.getName());

        public static String get(String key) {
            return get(key, null);
        }

        public static String get(String key, String defaultValue) {
            if (rb.containsKey(key))
                return rb.getString(key);
            else
                return defaultValue;
        }

    }

}
