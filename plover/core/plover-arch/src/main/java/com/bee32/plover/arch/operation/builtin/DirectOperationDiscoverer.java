package com.bee32.plover.arch.operation.builtin;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.operation.ClassOperationDiscoverer;
import com.bee32.plover.arch.operation.IOperation;
import com.bee32.plover.arch.operation.OperationContext;
import com.bee32.plover.arch.operation.OperationUtil;

/**
 * Only methods in the form of:
 *
 * <pre>
 * operation(HttpServletRequest, HttpServletResponse) -> *
 * </pre>
 *
 * will be treated as service operation.
 *
 * @see HttpServlet
 */
public class DirectOperationDiscoverer
        extends ClassOperationDiscoverer {

    @Override
    public int getPriority() {
        return -10;
    }

    @Override
    protected Map<String, IOperation> buildTypeOperationMap(Class<?> type) {
        Map<String, IOperation> operations = new HashMap<String, IOperation>();

        for (Method method : type.getMethods()) {
            Class<?>[] parameterTypes = method.getParameterTypes();

            if (parameterTypes.length != 1)
                continue;

            Class<?> singleType = parameterTypes[0];

            if (singleType == Object.class)
                continue;
            if (!singleType.isAssignableFrom(OperationContext.class))
                continue;

            String name = OperationUtil.getOperationName(method);
            if (name == null)
                name = method.getName();

            DirectOperation operation = new DirectOperation(name, method);

            operations.put(name, operation);
        }

        return operations;
    }

}
