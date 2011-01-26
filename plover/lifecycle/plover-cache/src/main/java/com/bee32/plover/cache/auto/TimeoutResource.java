package com.bee32.plover.cache.auto;

import com.bee32.plover.cache.CacheRetrieveException;
import com.bee32.plover.cache.ref.CacheRef;

/**
 * In GNU Make, if a prerequsite has a timestamp in future, it will raise a warning:
 *
 * <pre>
 * make: Warning: File `x' has modification time 3.7e+08 s in the future
 * make: warning:  Clock skew detected.  Your build may be incomplete.
 * </pre>
 *
 * So, here we'll just return the timeout value, using a single clock system overall.
 */
public class TimeoutResource
        extends VirtualResource<Void> {

    private long timeout;

    public TimeoutResource(IMakeSchema cacheSchema, long timeout) {
        super(cacheSchema);
        this.timeout = timeout;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public long getVersion() {
        return timeout;
    }

    @Override
    public Void pull()
            throws CacheRetrieveException {
        return null;
    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public CacheRef<Void> getCached() {
        return null;
    }

}
