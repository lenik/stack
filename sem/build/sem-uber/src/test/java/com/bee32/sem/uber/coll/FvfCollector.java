package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.test.ServiceCollector;
import com.bee32.plover.web.faces.FacesVolatileFragments;

public class FvfCollector
        extends ServiceCollector<FacesVolatileFragments> {

    public static void main(String[] args)
            throws IOException {
        new FvfCollector().collect();
    }

}
