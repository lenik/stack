package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.test.ServiceCollector;

public class ModuleCollector
        extends ServiceCollector<IModule> {

    public static void main(String[] args)
            throws IOException {
        new ModuleCollector().collect();
    }

}
