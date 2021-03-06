package com.bee32.plover.disp.type;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.disp.AbstractDispatcher;
import com.bee32.plover.disp.DispatchConfig;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.util.Arrival;
import com.bee32.plover.disp.util.IArrival;
import com.bee32.plover.disp.util.ITokenQueue;

public class MethodDispatcher
        extends AbstractDispatcher {

    public MethodDispatcher() {
        super();
    }

    public MethodDispatcher(String name) {
        super(name);
    }

    @Override
    public int getOrder() {
        return DispatchConfig.ORDER_METHOD;
    }

    private transient ClassMap<MethodKey, Method> classMap;
    {
        classMap = new ClassMap<MethodKey, Method>();
    }

    @Override
    public IArrival dispatch(IArrival context, ITokenQueue tokens)
            throws DispatchException {
        Object obj = context.getTarget();
        if (obj == null)
            return null;

        String methodName = tokens.peek();
        if (methodName == null)
            return null;

        String decoration;
        Class<?>[] wantPV = null;

        int availableParameters = tokens.available() - 1;

        int colon = methodName.indexOf(':');
        if (colon == -1) {
            if (availableParameters == 0)
                decoration = "";
            else
                decoration = "S";
        } else {
            decoration = methodName.substring(colon + 1);
            methodName = methodName.substring(0, colon);
        }

        if (availableParameters < decoration.length())
            return null;

        wantPV = new Class<?>[decoration.length()];
        Object[] parsedPV = new Object[wantPV.length];

        for (int i = 0; i < wantPV.length; i++) {
            char decor = decoration.charAt(i);
            String token = tokens.peek(i + 1);

            Class<?> decorType;
            Object parameter;
            switch (decor) {
            case 'S':
                decorType = String.class;
                parameter = token;
                break;

            case 'i':
                decorType = int.class;
                parameter = Integer.parseInt(token);
                break;

            case 'I':
                decorType = Integer.class;
                parameter = Integer.valueOf(token);
                break;

            case 'l':
                decorType = long.class;
                parameter = Long.parseLong(token);
                break;

            case 'L':
                decorType = Long.class;
                parameter = Long.valueOf(token);
                break;

            case 'N':
                decorType = BigInteger.class;
                parameter = new BigInteger(token);
                break;

            case 'D':
                decorType = BigDecimal.class;
                parameter = new BigDecimal(token);
                break;

            default:
                throw new DispatchException("Invalid decoration char: " + decor);
            }
            wantPV[i] = decorType;
            parsedPV[i] = parameter;
        }

        Class<? extends Object> contextClass = obj.getClass();
        MethodKey wantKey = new MethodKey(methodName, wantPV);

        Map<MethodKey, Method> methodMap = classMap.get(contextClass);
        if (methodMap == null) {
            methodMap = new HashMap<MethodKey, Method>();

            Method[] methods = contextClass.getMethods();
            for (Method m : methods) {
                String mName = m.getName();
                Class<?>[] mPV = m.getParameterTypes();

                MethodKey mKey = new MethodKey(mName, mPV);
                methodMap.put(mKey, m);

                // // Add xxx() as alias for getXxx().
                // if (mName.startsWith("get") && mName.length() > 3) {
                // mName = Strings.lcfirst(mName.substring(3));
                // mKey = new MethodKey(mName, mPT);
                // if (!methodMap.containsKey(mKey))
                // methodMap.put(mKey, m);
                // }
            }

            classMap.put(contextClass, methodMap);
        }

        Method method = methodMap.get(wantKey);
        if (method == null)
            return null;

        int consumed = 1 + wantPV.length;
        String[] consumedTokens = tokens.shift(consumed);

        Object result;
        try {
            result = method.invoke(obj, parsedPV);
        } catch (Exception e) {
            throw new DispatchException(e);
        }

        return new Arrival(context, result, consumedTokens);
    }
}

class MethodKey
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String methodName;
    private final Class<?>[] parameterTypes;

    public MethodKey(String methodName, Class<?>[] parameterTypes) {
        if (methodName == null)
            throw new NullPointerException("methodName");
        if (parameterTypes == null)
            throw new NullPointerException("parameterTypes");
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + methodName.hashCode();
        result = prime * result + Arrays.hashCode(parameterTypes);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MethodKey other = (MethodKey) obj;
        if (!methodName.equals(other.methodName))
            return false;
        if (!Arrays.equals(parameterTypes, other.parameterTypes))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(methodName);
        buf.append("(");
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i != 0)
                buf.append(',');
            buf.append(parameterTypes[i].getName());
        }
        buf.append(")");
        return buf.toString();
    }

}
