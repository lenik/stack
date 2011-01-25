package com.bee32.plover.cache.pull;

public class FriendlyAtomicClock
        extends TickClock {

    public FriendlyAtomicClock() {
        super(0);
    }

    public FriendlyAtomicClock(long start) {
        super(start);
    }

}
