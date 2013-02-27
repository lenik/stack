package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.servlet.test.AbstractQuitListener;
import com.bee32.plover.servlet.test.IQuitListener;
import com.bee32.plover.test.ServiceCollector;

/**
 * @see AbstractQuitListener
 */
public class QlCollector
        extends ServiceCollector<IQuitListener> {

    public static void main(String[] args)
            throws IOException {
        new QlCollector().collect();
    }

}
