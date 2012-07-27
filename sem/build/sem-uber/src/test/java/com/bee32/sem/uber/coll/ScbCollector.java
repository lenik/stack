package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.site.cfg.ISiteConfigBlock;
import com.bee32.plover.test.ServiceCollector;

public class ScbCollector
        extends ServiceCollector<ISiteConfigBlock> {

    public static void main(String[] args)
            throws IOException {
        new ScbCollector().collect();
    }

}
