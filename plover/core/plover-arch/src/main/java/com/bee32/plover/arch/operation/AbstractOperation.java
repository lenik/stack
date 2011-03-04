package com.bee32.plover.arch.operation;

import javax.free.INegotiation;
import javax.free.NegotiationParameter;

public abstract class AbstractOperation
        implements IOperation {

    public abstract Class<?>[] getParameterTypes();

    @Override
    public Object execute(Object instance, INegotiation negotiation)
            throws Exception {
        Class<?>[] parameterTypes = getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];

        for (NegotiationParameter np : negotiation) {
            int paramIndex = -1;

            String npId = np.getId();
            if (isNumeric(npId)) {
                // indexed-parameter
                paramIndex = Integer.valueOf(npId);
            }

            else {
                String npTypeName = npId;
                for (int index = 0; index < parameterTypes.length; index++) {
                    Class<?> paramType = parameterTypes[index];
                    String typeName = paramType.getName();
                    if (npTypeName.equals(typeName)) {
                        // type-matching parameter
                        paramIndex = index;
                        break;
                    }
                }
            }

            if (paramIndex != -1) {
                Object paramValue = np.getValue();
                // Class<?> paramType = parameterTypes[paramIndex];
                // parameters[paramIndex] = paramType.cast(paramValue);
                parameters[paramIndex] = paramValue;
            } else {
                np.bypass();
            }
        } // for negotiation-parameter

        return execute(instance, parameters);
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

    @Override
    public String toString() {
        return getName();
    }

}
