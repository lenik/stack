package com.bee32.plover.test;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnitTestCase<T>
        extends Assert {

    protected final Logger logger;

    public UnitTestCase() {
        logger = LoggerFactory.getLogger(getClass());
    }

    /**
     * Create a new instance like this using JUnit wrapper,
     *
     * @return Wrapped instance.
     */
    public T unit() {
        return unit(true);
    }

    /**
     * Wrap and wire this object, result in a new one if <code>dropThis</code> is specified, or hack
     * myself if <code>dropThis</code> is disabled. Spring CDI.
     *
     * @param dropThis
     *            Whether should hack into myself.
     *            <p>
     *            Currently, keep-this is not supported. A new instance should always be created.
     * @return Wrapped & Wired instance.
     */
    public T unit(boolean dropThis) {
        if (!dropThis)
            throw new UnsupportedOperationException("Sorry, it's unsupported to reuse this object.");

        T wrapped = (T) JUnitHelper.createWrappedInstance(getClass());
        return wrapped;
    }

    public void run() {
    }

    public void dump() {
    }

}
