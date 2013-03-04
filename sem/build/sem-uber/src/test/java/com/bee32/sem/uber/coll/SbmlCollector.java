
package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.servlet.test.AbstractSbml;
import com.bee32.plover.servlet.test.ISystemBcastMessageListener;
import com.bee32.plover.test.ServiceCollector;

/**
 * @see AbstractSbml
 */
public class SbmlCollector
        extends ServiceCollector<ISystemBcastMessageListener> {

    public static void main(String[] args)
            throws IOException {
        new SbmlCollector().collect();
    }

}
