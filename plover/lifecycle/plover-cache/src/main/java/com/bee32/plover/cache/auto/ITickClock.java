package com.bee32.plover.cache.auto;

public interface ITickClock {

    boolean isSystemClock();

    long current();

    long tick();

    String format(long tick);

    int compare(long a, long b);

}
