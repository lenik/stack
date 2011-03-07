package com.bee32.plover.arch;

import org.junit.After;
import org.junit.Before;

public abstract class SupportLibrary
        implements ISupportLibrary {

    @Before
    @Override
    public abstract void startup()
            throws Exception;

    @After
    @Override
    public abstract void shutdown()
            throws Exception;

}
