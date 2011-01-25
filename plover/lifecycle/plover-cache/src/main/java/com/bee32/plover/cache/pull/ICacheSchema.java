package com.bee32.plover.cache.pull;

public interface ICacheSchema {

    TickClock getClock();

    void setClock(TickClock clock);

}
