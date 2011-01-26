package com.bee32.plover.cache.auto;

public class SystemClock
        implements ITickClock {

    private final long initial;
    private long last;

    SystemClock() {
        initial = System.currentTimeMillis();
        last = initial;
    }

    @Override
    public boolean isSystemClock() {
        return true;
    }

    @Override
    public long current() {
        long tick = System.currentTimeMillis();
        if (tick < last)
            tick = last;
        return tick;
    }

    @Override
    public long tick() {
        long tick = System.currentTimeMillis();
        if (tick <= last)
            tick = ++last;
        else
            last = tick;
        return tick;
    }

    @Override
    public String format(long tick) {
        long offset = tick -= initial;
        return String.valueOf(offset);
    }

    @Override
    public int compare(long a, long b) {
        return a < b ? -1 : a > b ? 1 : 0;
    }

    private static final SystemClock instance = new SystemClock();

    public static SystemClock getInstance() {
        return instance;
    }

}
