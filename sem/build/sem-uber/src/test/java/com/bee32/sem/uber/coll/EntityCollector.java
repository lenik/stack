package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.test.ServiceCollector;

public class EntityCollector
        extends ServiceCollector<Entity<?>> {

    public static void main(String[] args)
            throws IOException {
        EntityCollector collector = new EntityCollector();
        collector.setShowPaths(true);
        collector.collect();
    }

}
