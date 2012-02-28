package com.bee32.plover.orm.util;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Set;

import javax.free.IdentityHashSet;
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

    public SamplePackage() {
        this(null);
    }

    public SamplePackage(String name) {
        super(name, true);
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

    public Set<SamplePackage> getAllDependencies() {
        @SuppressWarnings("unchecked")
        Set<SamplePackage> all = (Set<SamplePackage>) (Set<?>) new IdentityHashSet();
        getAllDependencies(all);
        return all;
    }

    void getAllDependencies(Set<SamplePackage> all) {
        if (all.add(this))
            for (SamplePackage dep : getDependencies())
                dep.getAllDependencies(all);
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

    public final SampleList getSamples() {
        assembleOnce();
        SampleList samples = new SampleList();
        getSamples(samples);
        return samples;
    }

    @Override
    protected final void assemble() {
        wireUp();

        // scan dependencies from injected fields.
        for (Field field : getClass().getDeclaredFields()) {
            if (SamplePackage.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                try {
                    SamplePackage dep = (SamplePackage) field.get(this);
                    addDependency(dep);
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
    }

    protected void wireUp() {
    }

    @Override
    protected void postAssemble() {
        SampleList samples = new SampleList();
        getSamples(samples);
        for (Entity<?> sample : samples) {
            switch (getLevel()) {
            case LEVEL_BAD:
                EntityAccessor.getFlags(sample).setWarn(true);
            case LEVEL_NORMAL:
                EntityAccessor.getFlags(sample).setTestData(true);
                EntityAccessor.getFlags(sample).setBuiltinData(false);
                break;
            case LEVEL_STANDARD:
                EntityAccessor.getFlags(sample).setTestData(false);
                EntityAccessor.getFlags(sample).setBuiltinData(true);
                break;
            }
        }
    }

    protected void getSamples(SampleList samples) {
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
            samples.add(entity);
        }
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

    protected void postSave(DataPartialContext data) {
    }

    public static SamplePackageAllocation allocation = SamplePackageAllocation.BOOTSTRAP;

    protected <S extends SamplePackage> S predefined(Class<? extends S> samplesClass) {
        S dep = allocation.getObject(samplesClass);
        addDependency(dep);
        return dep;
    }

}
