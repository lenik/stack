package com.bee32.plover.util;

import com.bee32.plover.arch.util.TextMap;

public interface IVariablesInside {

    String[] getVariableNames();

    /**
     * Evaluate a variable name.
     *
     * @return <code>null</code> if the variable is unknown.
     */
    Object evaluate(String variableName, TextMap params);

}
