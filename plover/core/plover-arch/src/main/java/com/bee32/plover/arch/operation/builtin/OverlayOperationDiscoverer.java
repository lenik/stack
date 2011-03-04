package com.bee32.plover.arch.operation.builtin;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import overlay.OverlayUtil;

import com.bee32.plover.arch.operation.ClassOperationDiscoverer;
import com.bee32.plover.arch.operation.IOperation;

public class OverlayOperationDiscoverer
        extends ClassOperationDiscoverer {

    @Override
    protected Map<String, IOperation> buildTypeOperationMap(Class<?> type) {
        Class<?> overlay = OverlayUtil.getOverlay(type, "operations");
        if (overlay == null)
            return Collections.emptyMap();

        Map<String, IOperation> operations = new HashMap<String, IOperation>();

        for (Method method : overlay.getMethods()) {
            int modifiers = method.getModifiers();
            if (!Modifier.isStatic(modifiers))
                continue;

            String name = method.getName();
            if (name.startsWith("_"))
                continue;

            MethodOperation operation = new MethodOperation(method);
            operations.put(name, operation);
        }

        return operations;
    }

}
