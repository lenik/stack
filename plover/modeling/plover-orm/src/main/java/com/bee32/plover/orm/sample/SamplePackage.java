package com.bee32.plover.orm.sample;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Set;

import javax.free.IdentityHashSet;
import javax.free.LinkedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.Assembled;
import com.bee32.plover.arch.util.IPriority;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.util.DataPartialContext;
import com.bee32.plover.site.scope.PerSite;

@ComponentTemplate
@PerSite
public abstract class SamplePackage
        extends Assembled
        implements IPriority {

    static Logger logger = LoggerFactory.getLogger(SamplePackage.class);

    public static String SECTION = "samples";

    public static final int LEVEL_STANDARD = -1;
    public static final int LEVEL_NORMAL = 0;
    public static final int LEVEL_BAD = 1;

    public static final int PRIORITY_SYSTEM = -10;
    public static final int PRIORITY_NORMAL = 0;
    public static final int PRIORITY_EXTRA = 10;

    private int seq;
    private final Set<SamplePackage> dependencies = new LinkedSet<SamplePackage>();

    public SamplePackage() {
        this(null);
    }

    public SamplePackage(String name) {
        super(name, true);
        SuperSamplePackage superPackage = getSuperPackage();
        if (superPackage != null)
            superPackage.insert(this);
    }

    @Override
    public int getPriority() {
        return PRIORITY_NORMAL;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public boolean isVirtual() {
        return false;
    }

    public int getLevel() {
        return LEVEL_NORMAL;
    }

    protected SuperSamplePackage getSuperPackage() {
        return null;
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

    public final SampleList getSamples(boolean grouped) {
        assembleOnce();
        SampleList samples = new SampleList();
        getSamples(samples, grouped);
        return samples;
    }

    @Override
    protected void wireUp() {
    }

    @Override
    protected void postprocess() {
        SampleList samples = new SampleList();
        getSamples(samples, false);
        for (Entity<?> sample : samples)
            decorate(sample);
    }

    protected void decorate(Entity<?> sample) {
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

    protected void getSamples(SampleList samples, boolean grouped) {
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
            EntityAccessor.setDeclaringField(entity, this, field);
            samples.add(field.getName(), entity);
        }

        // Think about: unit hierarchy => microgroups.
        if (grouped)
            if (!samples.isEmpty()) {
                Entity<?> first = samples.get(0);
                Entity<?> prev = first;
                for (int i = 1; i < samples.size(); i++) {
                    Entity<?> next = samples.get(i);
                    EntityAccessor.setNextOfMicroLoop(prev, next);
                    prev = next;
                }
                samples.clear();
                samples.add(null/* alt-id */, first);
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
