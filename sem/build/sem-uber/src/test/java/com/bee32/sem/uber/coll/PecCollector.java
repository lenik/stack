package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.ape.engine.beans.IProcessEngineConfigurer;
import com.bee32.plover.test.ServiceCollector;

public class PecCollector
        extends ServiceCollector<IProcessEngineConfigurer> {

    public static void main(String[] args)
            throws IOException {
        new PecCollector().collect();
    }

}
