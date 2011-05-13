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
        return unit(false);
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
        if (dropThis) {
            T wrapped = (T) JUnitHelper.createWrappedInstance(getClass());
            return wrapped;
        }

        @SuppressWarnings("unchecked")
        T _this = (T) this;

        try {
            JUnitHelper.beforeClass(getClass());
            JUnitHelper.beforeMethod(getClass(), this);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return _this;
    }

    public void run() {
    }

    public void dump() {
    }

}
