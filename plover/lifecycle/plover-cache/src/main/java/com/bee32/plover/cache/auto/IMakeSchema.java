package com.bee32.plover.cache.auto;

import com.bee32.plover.cache.ICacheAllocator;

public interface IMakeSchema {

    ITickClock getClock();

    /**
     * Useful for -B build
     */
    boolean isForceToCreate(CreateRuleResource<?> resource);

    ICacheAllocator getCacheAllocator();

}
