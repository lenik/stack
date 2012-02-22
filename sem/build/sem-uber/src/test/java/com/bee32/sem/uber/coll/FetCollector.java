package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.faces.IFacesExceptionTranslator;
import com.bee32.plover.test.ServiceCollector;

public class FetCollector
        extends ServiceCollector<IFacesExceptionTranslator> {

    public static void main(String[] args)
            throws IOException {
        new FetCollector().collect();
    }

}
