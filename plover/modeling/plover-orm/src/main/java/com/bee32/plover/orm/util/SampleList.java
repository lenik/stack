package com.bee32.plover.orm.util;

import java.util.ArrayList;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;

public class SampleList
        extends ArrayList<Entity<?>> {

    private static final long serialVersionUID = 1L;

    @Deprecated
    @Override
    public boolean add(Entity<?> e) {
        return super.add(e);
    }

    public void add(String altId, Entity<?> sample) {
        sample.setAltId(altId);
        add(sample);
    }

    public void addBatch(Entity<?>... samples) {
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
