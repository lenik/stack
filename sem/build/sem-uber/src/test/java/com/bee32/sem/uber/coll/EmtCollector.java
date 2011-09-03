package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.test.ServiceCollector;
import com.bee32.plover.web.faces.IErrorMessageTranslator;

public class EmtCollector
        extends ServiceCollector<IErrorMessageTranslator> {

    public static void main(String[] args)
            throws IOException {
        new EmtCollector().collect();
    }

}
