package com.bee32.plover.javascript;

import java.util.Set;

public interface IDependent<T extends IDependent<T>> {

    Set<? extends T> getDependencies();

    Set<? extends T> mergeDependencies();

    boolean dependsOn(T dependency);

}
