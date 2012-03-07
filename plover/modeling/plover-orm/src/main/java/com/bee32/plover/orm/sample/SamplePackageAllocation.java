package com.bee32.plover.orm.sample;

import java.util.LinkedHashMap;
import java.util.Map;

import com.bee32.plover.orm.util.BootstrapDataAssembledContext;

public abstract class SamplePackageAllocation {

    public abstract <S extends SamplePackage> S getObject(Class<? extends S> samplesClass);

    public static final SamplePackageAllocation BOOTSTRAP = new BootstrapSamplePackageAllocation();
    public static final SamplePackageAllocation STATIC = new StaticSamplePackageAllocation();

}

class StaticSamplePackageAllocation
        extends SamplePackageAllocation {

    static final Map<Class<?>, SamplePackage> registry;
    static {
        registry = new LinkedHashMap<Class<?>, SamplePackage>();
    }

    @Override
    public synchronized <S extends SamplePackage> S getObject(Class<? extends S> samplesClass) {
        S obj = (S) registry.get(samplesClass);
        if (obj == null) {
            try {
                obj = samplesClass.newInstance();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            registry.put(samplesClass, obj);
        }
        return obj;
    }

}

class BootstrapSamplePackageAllocation
        extends SamplePackageAllocation {

    public <S extends SamplePackage> S getObject(Class<? extends S> samplesClass) {
        return BootstrapDataAssembledContext.bean.getBean(samplesClass);
    }

}