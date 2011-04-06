package com.bee32.plover.disp.util;

public interface ArrivalBacktraceCallback<E extends Exception> {

    /**
     * @return <code>false</code> to terminate the traversion.
     */
    boolean arriveBack(IArrival arrival)
            throws E;

}
