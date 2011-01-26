package com.bee32.plover.cache.auto;

import com.bee32.plover.cache.ICacheAllocator;
import com.bee32.plover.cache.ref.NonNullRef;

public class SimpleMakeSchema
        implements IMakeSchema {

    private ITickClock clock;

    private ICacheAllocator allocator;

    public SimpleMakeSchema() {
        clock = SystemClock.getInstance();
        allocator = NonNullRef.getAllocator();
    }

    public ITickClock getClock() {
        return clock;
    }

    public void setClock(ITickClock clock) {
        if (clock == null)
            throw new NullPointerException("clock");
        this.clock = clock;
    }

    @Override
    public boolean isForceToCreate(CreateRuleResource<?> resource) {
        return false;
    }

    @Override
    public ICacheAllocator getCacheAllocator() {
        return allocator;
    }

    public void setCacheAllocator(ICacheAllocator cacheAllocator) {
        if (cacheAllocator == null)
            throw new NullPointerException("cacheAllocator");
        this.allocator = cacheAllocator;
    }

}
