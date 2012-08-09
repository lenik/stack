package com.bee32.sem.term;

import com.bee32.plover.faces.AbstractFet;

public class TermExprFet
        extends AbstractFet {

    TermMessageInterpolator interpolator = TermMessageInterpolator.getInstance();

    @Override
    public String translate(Throwable exception, String message) {
        if (message != null)
            message = interpolator.process(message);
        return message;
    }

}
