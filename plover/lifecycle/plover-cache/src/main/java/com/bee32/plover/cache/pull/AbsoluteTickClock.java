package com.bee32.plover.cache.pull;

public class AbsoluteTickClock
        extends TickClock {

    public AbsoluteTickClock() {
        super(Long.MIN_VALUE);
    }

    public AbsoluteTickClock(long start) {
        super(Long.MIN_VALUE + start);
    }

}
