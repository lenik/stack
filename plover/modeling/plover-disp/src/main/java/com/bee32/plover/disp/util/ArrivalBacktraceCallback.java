package com.bee32.plover.disp.util;

import javax.servlet.ServletException;

public interface ArrivalBacktraceCallback {

    /**
     * @return <code>false</code> to terminate the traversion.
     */
    boolean arriveBack(IArrival arrival, ReversedPathTokens consumedRpt)
            throws ServletException;

}
