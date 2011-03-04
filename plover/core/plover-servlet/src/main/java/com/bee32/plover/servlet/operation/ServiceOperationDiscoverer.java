package com.bee32.plover.servlet.operation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bee32.plover.arch.operation.ClassOperationDiscoverer;
import com.bee32.plover.arch.operation.IOperation;
import com.bee32.plover.arch.operation.OperationUtil;
import com.bee32.plover.arch.operation.builtin.MethodOperation;

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
public class ServiceOperationDiscoverer
        extends ClassOperationDiscoverer {

    @Override
    protected Map<String, IOperation> buildTypeOperationMap(Class<?> type) {
        Map<String, IOperation> operations = new HashMap<String, IOperation>();

        for (Method method : type.getMethods()) {
            Class<?>[] parameterTypes = method.getParameterTypes();

            if (parameterTypes.length != 2)
                continue;

            if (!HttpServletRequest.class.equals(parameterTypes[0]))
                continue;

            if (!HttpServletResponse.class.equals(parameterTypes[1]))
                continue;

            String name = OperationUtil.getOperationName(method);
            if (name == null)
                name = method.getName();

            MethodOperation operation = new MethodOperation(method);

            operations.put(name, operation);
        }

        return operations;
    }

}
