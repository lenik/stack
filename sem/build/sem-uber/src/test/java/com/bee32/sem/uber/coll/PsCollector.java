package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.servlet.central.PloverServlet;
import com.bee32.plover.test.ServiceCollector;

public class PsCollector
        extends ServiceCollector<PloverServlet> {

    public static void main(String[] args)
            throws IOException {
        new PsCollector().collect();
    }

}
