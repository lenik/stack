package com.bee32.plover.cache.auto;

public class FriendlySteppingClock
        extends SteppingClock {

    public FriendlySteppingClock() {
        super(0);
    }

    public FriendlySteppingClock(long start) {
        super(0 + start);
    }

}
