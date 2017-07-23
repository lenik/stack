package com.bee32.zebra;

import net.bodz.bas.shell.util.TypeCollectorApp;

import com.bee32.zebra.TestTypeCollector;

public class MainTypeCollector
        extends TypeCollectorApp {

    public static void main(String[] args)
            throws Exception {
        new TestTypeCollector().execute(args);
    }

}
