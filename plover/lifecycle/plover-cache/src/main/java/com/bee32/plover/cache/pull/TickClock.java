package com.bee32.plover.cache.pull;

public class TickClock {

    private static final long defaultStartTick = Long.MIN_VALUE;

    private long tick = defaultStartTick;

    public TickClock() {
        this(defaultStartTick);
    }

    public TickClock(long start) {
        this.tick = start;
    }

    public long tick() {
        return tick++;
    }

    @Override
    public String toString() {
        return "Current Tick:  " + tick;
    }

}
