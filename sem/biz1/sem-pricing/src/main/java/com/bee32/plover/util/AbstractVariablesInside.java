package com.bee32.plover.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.bee32.plover.arch.util.TextMap;

public abstract class AbstractVariablesInside
        implements IVariablesInside {

    transient String[] varNames;
    transient Map<String, VariableEntry> varMap;

    @Override
    public final synchronized String[] getVariableNames() {
        if (varNames == null) {
            Map<String, VariableEntry> map = scanVars();
            Set<String> nameSet = map.keySet();
            varNames = nameSet.toArray(new String[0]);
        }
        return varNames;
    }

    @Override
    public final Object evaluate(String variableName, TextMap params) {
        Map<String, VariableEntry> map;
        if (varMap == null)
            map = scanVars();
        else
            map = varMap;

        VariableEntry entry = map.get(variableName);
        if (entry == null)
            return null;
        else
            return entry.value;
    }

    synchronized void cacheVars() {
        varMap = scanVars();
    }

    void invalidateVars() {
        varMap = null;
    }

    private Map<String, VariableEntry> scanVars() {
        Map<String, VariableEntry> map = new HashMap<String, VariableEntry>();
        populateVariables(map);
        return map;

    }

    protected void populateVariables(Map<String, VariableEntry> varMap) {
    }

}
