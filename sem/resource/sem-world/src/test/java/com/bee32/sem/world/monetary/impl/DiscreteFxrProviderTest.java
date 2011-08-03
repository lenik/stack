package com.bee32.sem.world.monetary.impl;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.FxrTable;

public class DiscreteFxrProviderTest
        extends Assert {

    DiscreteFxrProvider fxp = new DiscreteFxrProvider();
    FxrSamplesSource source = new FxrSamplesSource();

    @Test
    public void testCommit()
            throws FxrQueryException {

        for (FxrTable table : source)
            fxp.commit(table);

        fxp.getFxrTable(null);
    }

}
