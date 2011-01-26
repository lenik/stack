package com.bee32.plover.cache.auto;

public class AbsoluteSteppingClock
        extends SteppingClock {

    public AbsoluteSteppingClock() {
        super(Long.MIN_VALUE);
    }

    public AbsoluteSteppingClock(long start) {
        super(Long.MIN_VALUE + start);
    }

}
