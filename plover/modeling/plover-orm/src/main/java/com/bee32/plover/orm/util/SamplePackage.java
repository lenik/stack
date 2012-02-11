package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.free.LinkedSet;

import com.bee32.plover.arch.Component;
import com.bee32.plover.arch.util.IPriority;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;

public class SamplePackage
        extends Component
        implements IPriority {

    protected static class ctx
            extends BootstrapDataAssembledContext {
    }

    private final List<Entity<?>> instances = new ArrayList<Entity<?>>();
    private final Set<SamplePackage> dependencies = new LinkedSet<SamplePackage>();

    private int priority;

    public SamplePackage() {
        super();
    }

    public SamplePackage(String name) {
        super(name);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isVirtual() {
        return false;
    }

    public static final int BAD = -1;
    public static final int NORMAL = 0;
    public static final int STANDARD = 1;

    public int getLevel() {
        return NORMAL;
    }

    public List<Entity<?>> getInstances() {
        return Collections.unmodifiableList(instances);
    }

    public Set<SamplePackage> getDependencies() {
        return Collections.unmodifiableSet(dependencies);
    }

    public void addInstance(Entity<? extends Serializable> instance) {
        if (instance == null)
            throw new NullPointerException("instance");
        switch (getLevel()) {
        case BAD:
            EntityAccessor.getFlags(instance).setWarn(true);
        case NORMAL:
            EntityAccessor.getFlags(instance).setTestData(true);
            EntityAccessor.getFlags(instance).setBuiltinData(false);
            break;
        case STANDARD:
            EntityAccessor.getFlags(instance).setTestData(false);
            EntityAccessor.getFlags(instance).setBuiltinData(true);
            break;
        }
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

    protected void more() {
    }

}
