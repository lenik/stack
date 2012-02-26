package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.free.LinkedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.Assembled;
import com.bee32.plover.arch.util.IPriority;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;

public class SamplePackage
        extends Assembled
        implements IPriority {

    protected static class ctx
            extends BootstrapDataAssembledContext {
    }

    static Logger logger = LoggerFactory.getLogger(SamplePackage.class);

    public static final int LEVEL_BAD = -1;
    public static final int LEVEL_NORMAL = 0;
    public static final int LEVEL_STANDARD = 1;

    private int priority;
    private final Set<SamplePackage> dependencies = new LinkedSet<SamplePackage>();
    private final List<Entity<?>> instances = new ArrayList<Entity<?>>();

    public SamplePackage() {
        this(null);
    }

    public SamplePackage(String name) {
        super(name, true);
        ImportSamplesUtil.register(this);
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

    public int getLevel() {
        return LEVEL_NORMAL;
    }

    public Set<SamplePackage> getDependencies() {
        assembleOnce();
        return Collections.unmodifiableSet(dependencies);
    }

    public void addDependency(SamplePackage dependency) {
        if (dependency == null)
            throw new NullPointerException("dependency");
        dependencies.add(dependency);
    }

    public List<Entity<?>> getInstances() {
        assembleOnce();
        return Collections.unmodifiableList(instances);
    }

    protected void listSamples() {
        for (Field field : getClass().getDeclaredFields()) {
            if (!Entity.class.isAssignableFrom(field.getType()))
                continue;
            field.setAccessible(true);
            Entity<?> entity;
            try {
                entity = (Entity<?>) field.get(this);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            add(entity);
        }
    }

    public void add(Entity<? extends Serializable> instance) {
        if (instance == null)
            throw new NullPointerException("instance");
        switch (getLevel()) {
        case LEVEL_BAD:
            EntityAccessor.getFlags(instance).setWarn(true);
        case LEVEL_NORMAL:
            EntityAccessor.getFlags(instance).setTestData(true);
            EntityAccessor.getFlags(instance).setBuiltinData(false);
            break;
        case LEVEL_STANDARD:
            EntityAccessor.getFlags(instance).setTestData(false);
            EntityAccessor.getFlags(instance).setBuiltinData(true);
            break;
        }
        instances.add(instance);
    }

    @SafeVarargs
    protected final <E extends Entity<?>> void addBulk(E... samples) {
        for (Entity<?> sample : samples)
            add(sample);
    }

    @SafeVarargs
    protected final <E extends Entity<?>> void addMicroGroup(E... samples) {
        if (samples.length == 0)
            return;

        E head = samples[0];
        E prev = head;
        for (int i = 1; i < samples.length; i++) {
            E node = samples[i];
            EntityAccessor.setNextOfMicroLoop(prev, node);
        }

        add(head);
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

    protected void postSave() {
    }

}
