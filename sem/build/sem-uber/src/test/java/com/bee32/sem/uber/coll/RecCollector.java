package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.site.IRequestEntryCompletion;
import com.bee32.plover.test.ServiceCollector;

public class RecCollector
        extends ServiceCollector<IRequestEntryCompletion> {

    public static void main(String[] args)
            throws IOException {
        new RecCollector().collect();
    }

}
