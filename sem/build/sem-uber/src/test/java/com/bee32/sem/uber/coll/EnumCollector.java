package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.test.ServiceCollector;

public class EnumCollector
        extends ServiceCollector<EnumAlt<?, ?>> {

    public static void main(String[] args)
            throws IOException {
        new EnumCollector().collect();
    }

}
