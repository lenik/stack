package com.bee32.sem.term;

import com.bee32.plover.arch.util.res.AbstractNlsMessageProcessor;

public class TermExprNlsMessageProcessor
        extends AbstractNlsMessageProcessor {

    TermMessageInterpolator interpolator = TermMessageInterpolator.getInstance();

    @Override
    public String processMessage(String message) {
        try {
            return interpolator.process(message);
        }catch (Exception e) {
            e.printStackTrace();
            return message;
        }
    }

}
