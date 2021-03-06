package com.bee32.plover.orm.sample;

import java.util.ArrayList;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;

public class SampleList
        extends ArrayList<Entity<?>> {

    private static final long serialVersionUID = 1L;

    /**
     * @deprecated add anonymous entity sample is deprecated, an alt-Id should be given.
     * @see #add(String, Entity)
     */
    @Deprecated
    @Override
    public final boolean add(Entity<?> e) {
        return super.add(e);
    }

    public final void add(String altId, Entity<?> sample) {
        if (altId != null)
            sample.setAltId(altId);
        super.add(sample);
    }

    public final void addBatch(Entity<?>... samples) {
        for (Entity<?> sample : samples)
            add(sample);
    }

    public void addMicroGroup(Entity<?>... samples) {
        Entity<?> head = microGroup(samples);
        add(head);
    }

    @SafeVarargs
    protected static <E extends Entity<?>> E microGroup(E... samples) {
        if (samples.length == 0)
            return null;

        E head = samples[0];
        E prev = head;
        for (int i = 1; i < samples.length; i++) {
            E node = samples[i];
            EntityAccessor.setNextOfMicroLoop(prev, node);
        }
        return head;
    }

}
