package com.bee32.plover.javascript;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Dependent<T extends IDependent<T>>
        implements IDependent<T> {

    private final Set<T> dependencies;

    public Dependent() {
        this.dependencies = new HashSet<T>();
    }

    public Dependent(Comparator<? super T> comparator) {
        this.dependencies = new TreeSet<T>(comparator);
    }

    @Override
    public Collection<T> getDependencies() {
        return dependencies;
    }

    @Override
    public Collection<T> getReducedDependencies() {
        Set<T> onceSet = new HashSet<T>();
        for (T dependency : dependencies) {
            for (T once : onceSet) {
                boolean implied = once.isDepended(dependency);
                if (!implied)
                    onceSet.add(dependency);
            }
        }
        return onceSet;
    }

    @Override
    public boolean isDepended(T dependency) {
        for (T declared : dependencies)
            if (declared.isDepended(dependency))
                return true;
        return false;
    }

    public void addDependency(T dependency) {
        this.dependencies.add(dependency);
    }

    public void removeDependency(T dependency) {
        this.dependencies.remove(dependency);
    }

}
