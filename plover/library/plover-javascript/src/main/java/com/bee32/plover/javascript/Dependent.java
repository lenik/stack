package com.bee32.plover.javascript;

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
    public Set<T> getDependencies() {
        return dependencies;
    }

    @Override
    public Set<T> mergeDependencies() {
        Set<T> all = new HashSet<T>();
        for (T dependency : dependencies) {
            for (T a : all) {
                if (a.dependsOn(dependency))
                    continue;
                all.add(dependency);
            }
        }
        return all;
    }

    @Override
    public boolean dependsOn(T dependency) {
        for (T declared : dependencies)
            if (declared.dependsOn(dependency))
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
