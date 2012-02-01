package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.faces.FacesVolatileFragments;
import com.bee32.plover.test.ServiceCollector;

public class FvfCollector
        extends ServiceCollector<FacesVolatileFragments> {

    public static void main(String[] args)
            throws IOException {
        new FvfCollector().collect();
    }

}
