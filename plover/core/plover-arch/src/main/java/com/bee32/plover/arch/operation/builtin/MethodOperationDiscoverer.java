package com.bee32.plover.arch.operation.builtin;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.operation.ClassOperationDiscoverer;
import com.bee32.plover.arch.operation.IOperation;
import com.bee32.plover.arch.operation.OperationUtil;

public class MethodOperationDiscoverer
        extends ClassOperationDiscoverer {

    @Override
    protected Map<String, IOperation> buildTypeOperationMap(Class<?> type) {
        Map<String, IOperation> map = new HashMap<String, IOperation>();

        for (Method method : type.getMethods()) {
            // String name = method.getName();
            String name = OperationUtil.getOperationName(method);
            if (name == null)
                continue;

            MethodOperation operation = new MethodOperation(method);
            map.put(name, operation);
        }

        return map;
    }

}
