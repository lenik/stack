package com.bee32.plover.javascript;

import java.util.Collection;

public interface IDependent<T extends IDependent<T>> {

    Collection<T> getDependencies();

    Collection<T> getReducedDependencies();

    boolean isDepended(T dependency);

}
