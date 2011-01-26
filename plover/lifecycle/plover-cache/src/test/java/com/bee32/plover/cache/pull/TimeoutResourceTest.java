package com.bee32.plover.cache.pull;

import org.junit.Test;

import com.bee32.plover.cache.CacheRetrieveException;
import com.bee32.plover.cache.auto.FriendlySteppingClock;
import com.bee32.plover.cache.auto.IMakeSchema;
import com.bee32.plover.cache.auto.ITickClock;
import com.bee32.plover.cache.auto.SimpleMakeSchema;
import com.bee32.plover.cache.auto.TimeoutResource;

public class TimeoutResourceTest
        extends AbstractResourceTest {

    ITickClock clock;

    @Override
    protected IMakeSchema newSchema() {
        clock = new FriendlySteppingClock();
        SimpleMakeSchema schema = new SimpleMakeSchema();
        schema.setClock(clock);
        return schema;
    }

    @Test
    public void testShortTimeout()
            throws CacheRetrieveException {
        CreationCountResource ccr = new CreationCountResource(schema);

        TimeoutResource timeoutRes = new TimeoutResource(schema,//
                clock.current() + 3);
        // time = 0
        ccr.addDependency(timeoutRes);

        assertEquals(0, ccr.getCreateCount());

        for (int repeatPull = 0; repeatPull < 1000; repeatPull++) {
            ccr.pull();
            assertEquals(1, ccr.getCreateCount());
        }

        clock.tick(); // time 1
        ccr.pull();
        assertEquals(1, ccr.getCreateCount());

        clock.tick(); // time 2
        ccr.pull();
        assertEquals(1, ccr.getCreateCount());

        clock.tick(); // time 3
        ccr.pull();
        assertEquals(2, ccr.getCreateCount());

    }

}
