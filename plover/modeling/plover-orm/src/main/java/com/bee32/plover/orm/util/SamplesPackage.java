package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.bee32.plover.arch.Component;
import com.bee32.plover.orm.entity.Entity;

public class SamplesPackage
        extends Component {

    final Set<Entity<?>> instances = new HashSet<Entity<?>>();
    final Set<SamplesPackage> dependencies = new HashSet<SamplesPackage>();

    public SamplesPackage() {
        super();
    }

    public SamplesPackage(String name) {
        super(name);
    }

    public boolean isVirtual() {
        return false;
    }

    public Set<Entity<?>> getInstances() {
        return instances;
    }

    public Set<SamplesPackage> getDependencies() {
        return dependencies;
    }

    public void addInstance(Entity<? extends Serializable> instance) {
        if (instance == null)
            throw new NullPointerException("instance");
        instances.add(instance);
    }

    public void addDependency(SamplesPackage dependency) {
        if (dependency == null)
            throw new NullPointerException("dependency");
        dependencies.add(dependency);
    }
}
