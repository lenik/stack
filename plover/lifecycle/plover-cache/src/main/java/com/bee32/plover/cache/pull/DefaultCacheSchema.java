package com.bee32.plover.cache.pull;

public class DefaultCacheSchema
        implements ICacheSchema {

    private TickClock clock;

    public TickClock getClock() {
        return clock;
    }

    public void setClock(TickClock clock) {
        this.clock = clock;
    }

}
