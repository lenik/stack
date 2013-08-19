package com.bee32.sem.event;

import com.bee32.plover.arch.util.Flags32;

/**
 * 事件标识位
 *
 * <p lang="en">
 */
public class EventFlags
        extends Flags32 {

    private static final long serialVersionUID = 1L;

    public static final int LEVEL_MASK = 0x0000000f;
    public static final int NO_LEVEL = 0;
    public static final int CLEAR = 1;
    public static final int NOTICE = 2;
    public static final int WARNING = 3;
    public static final int ERROR = 4;
    public static final int FATAL = 5;

    public int getLevel() {
        return bits & LEVEL_MASK;
    }

    public void setLevel(int level) {
        bits &= LEVEL_MASK;
        bits |= level;
    }

}
