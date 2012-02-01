package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.faces.IFaceletExceptionHandler;
import com.bee32.plover.test.ServiceCollector;

public class FehCollector
        extends ServiceCollector<IFaceletExceptionHandler> {

    public static void main(String[] args)
            throws IOException {
        new FehCollector().collect();
    }

}
