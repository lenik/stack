package com.bee32.plover.arch.operation;

public class IndexedParameterMap
        implements IParameterMap {

    private final Object[] parameters;

    public IndexedParameterMap(Object[] parameters) {
        if (parameters == null)
            throw new NullPointerException("parameters");
        this.parameters = parameters;
    }

    public Object[] getParameters() {
        return parameters;
    }

    @Override
    public Object get(int parameterIndex) {
        if (parameterIndex < parameters.length)
            return parameters[parameterIndex];

        return null;
    }

    @Override
    public Object get(Object parameterKey) {
        int index = -1;

        if (parameterKey.getClass() == Integer.class)
            index = ((Integer) parameterKey).intValue();

        else if (parameterKey.getClass() == String.class) {
            String indexStr = parameterKey.toString();
            if (isNumeric(indexStr))
                index = Integer.parseInt(indexStr);
        }

        if (index != -1 && index < parameters.length)
            return parameters[index];

        return null;
    }

    private static boolean isNumeric(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (!Character.isDigit(ch))
                return false;
        }
        return true;
    }

}
