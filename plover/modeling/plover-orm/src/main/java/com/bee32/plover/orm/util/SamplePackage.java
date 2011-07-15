package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bee32.plover.arch.Component;
import com.bee32.plover.orm.entity.Entity;

public class SamplePackage
        extends Component {

    final List<Entity<?>> instances = new ArrayList<Entity<?>>();
    final Set<SamplePackage> dependencies = new HashSet<SamplePackage>();

    public SamplePackage() {
        super();
    }

    public SamplePackage(String name) {
        super(name);
    }

    public boolean isVirtual() {
        return false;
    }

    public List<Entity<?>> getInstances() {
        return instances;
    }

    public Set<SamplePackage> getDependencies() {
        return dependencies;
    }

    public void addInstance(Entity<? extends Serializable> instance) {
        if (instance == null)
            throw new NullPointerException("instance");
        instances.add(instance);
    }

    public void addDependency(SamplePackage dependency) {
        if (dependency == null)
            throw new NullPointerException("dependency");
        dependencies.add(dependency);
    }

    /**
     * This is a debug helper. You may set breakpoint here.
     */
    public void beginLoad() {
    }

    /**
     * This is a debug helper. You may set breakpoint here.
     */
    public void endLoad() {
    }

}
