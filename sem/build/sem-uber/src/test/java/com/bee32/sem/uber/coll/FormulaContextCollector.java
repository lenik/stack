package com.bee32.sem.uber.coll;

import java.io.IOException;

import com.bee32.plover.ox1.formula.IFormulaContext;
import com.bee32.plover.test.ServiceCollector;

public class FormulaContextCollector
        extends ServiceCollector<IFormulaContext> {

    public static void main(String[] args)
            throws IOException {
        new FormulaContextCollector().collect();
    }

}
