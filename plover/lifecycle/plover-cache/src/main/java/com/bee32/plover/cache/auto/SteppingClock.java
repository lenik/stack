package com.bee32.plover.cache.auto;

public class SteppingClock
        implements ITickClock {

    private final long tickBase;
    private long tick;

    public SteppingClock(long start) {
        this.tickBase = start;
        this.tick = start;
    }

    @Override
    public boolean isSystemClock() {
        return false;
    }

    public long current() {
        return tick;
    }

    public long tick() {
        return tick++;
    }

    @Override
    public String format(long tick) {
        return String.valueOf(tick - tickBase);
    }

    @Override
    public int compare(long a, long b) {
        return SteppingComparator.compare(a, b);
    }

    @Override
    public String toString() {
        return "Current Tick:  " + tick;
    }

}
