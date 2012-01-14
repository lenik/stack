package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.inject.NotAComponent;
import com.bee32.plover.test.ServiceCollector;

/**
 * Collect service collectors.
 */
/* Here exclude this collector, to avoid dead loop. */@NotAComponent
public class UniversalCollector
        extends ServiceCollector<ServiceCollector<?>> {

    @Override
    protected void scan() {
        for (Class<?> collector : getExtensions(serviceClass, false)) {
            Class<?> serviceClass2 = ClassUtil.infer1(collector, ServiceCollector.class, 0);

            System.out.println("Prototype: " + serviceClass2);
            for (Class<?> serviceClass2Impl : getExtensions(serviceClass2, true)) {
                // System.out.println("  Sub service: "+subservice);
                publish(serviceClass2, serviceClass2Impl);
            }
            System.out.println();
        }
    }

    public static void main(String[] args)
            throws IOException {
        new UniversalCollector().collect();
    }

}
