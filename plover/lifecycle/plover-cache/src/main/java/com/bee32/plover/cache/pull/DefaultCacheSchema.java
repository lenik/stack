package com.bee32.plover.cache.pull;

public class DefaultCacheSchema
        implements ICacheSchema {

    static final int defaultCycleSize = 1000000; // 1M

    private int cycleSize = defaultCycleSize;
    private int tick = 0;

    @Override
    public int tick() {
        int v = tick++;
        if (tick < 0)
            tick = 0;
        return v;
        // return tick++;
    }

    @Override
    public int compareSerialVersion(int a, int b) {
        if (a == b)
            return 0;
        if (a < b) {
            int deltaInWrap = a + (Integer.MAX_VALUE - b) + 1;
            if (deltaInWrap <= 0)
                return 1; // a < b
            else if (deltaInWrap <= cycleSize)
                return -1;
            else
                return 1;
        } else {
            int deltaInWrap = b + (Integer.MAX_VALUE - a) + 1;
            if (deltaInWrap <= 0)
                return -1; // a > b
            else if (deltaInWrap <= cycleSize)
                return 1;
            else
                return -1;
        }
    }

}
