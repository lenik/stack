package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.test.ServiceCollector;
import com.bee32.sem.frame.menu.MenuComposite;

public class McCollector
        extends ServiceCollector<MenuComposite> {

    public static void main(String[] args)
            throws IOException {
        new McCollector().collect();
    }

}
