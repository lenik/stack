package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.test.ServiceCollector;
import com.bee32.sem.term.ITermProvider;

public class TpCollector
        extends ServiceCollector<ITermProvider> {

    public static void main(String[] args)
            throws IOException {
        new TpCollector().collect();
    }

}
