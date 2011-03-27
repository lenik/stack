package com.bee32.icsf.access.annotation;

import java.lang.reflect.Method;

import com.bee32.icsf.access.builtins.PointPermission;
import com.bee32.plover.arch.ui.res.InjectedAppearance;
import com.bee32.plover.arch.util.res.UniquePrefixStrategy;

public class RestrictedUtil {

    public static void parse(Class<?> clazz) {

        UniquePrefixStrategy strategy = new UniquePrefixStrategy();

        for (Method method : clazz.getMethods()) {
            Restricted restricted = method.getAnnotation(Restricted.class);
            String name = restricted.name();
            if (name.isEmpty())
                name = method.getName();

            PointPermission perm = new PointPermission(name);
            InjectedAppearance appearance = new InjectedAppearance(perm, null, clazz);

            strategy.registerPrefix(name, appearance);
        }
    }

}
