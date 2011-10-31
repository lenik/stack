package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.test.ServiceCollector;
import com.bee32.plover.web.faces.IFaceletExceptionHandler;

public class FehCollector
        extends ServiceCollector<IFaceletExceptionHandler> {

    public static void main(String[] args)
            throws IOException {
        new FehCollector().collect();
    }

}
