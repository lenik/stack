package com.bee32.plover.orm.util;

import java.util.HashMap;
import java.util.Map;

public class SampleContributionManager {

    static final Map<Class<?>, SampleContribution> instances = new HashMap<Class<?>, SampleContribution>();

    public static boolean register(SampleContribution contrib) {
        Class<?> clazz = contrib.getClass();

        SampleContribution existing = instances.get(clazz);
        if (existing != null)
            return false;

        instances.put(clazz, contrib);
        return true;
    }

    public static synchronized SampleContribution getInstance(Class<? extends SampleContribution> clazz) {
        if (clazz == null)
            throw new NullPointerException("clazz");

        SampleContribution instance = instances.get(clazz);
        if (instance == null) {
            // lazy-create
            try {
                instance = clazz.newInstance();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e.getMessage(), e);
            }

            register(instance);

            clazz.getAnnotation(ImportSamples.class);
            SamplePackage _package = new SamplePackage(instance.getName());

            ImportSamples imports = getClass().getAnnotation(ImportSamples.class);
            if (imports != null) {
                for (Class<?> importClass : imports.value()) {
                    SampleContribution importInstance = instances.get(importClass);
                    if (importInstance == null) {
                        logger.warn("Samples contribution " + this + " imports non-existing contribution "
                                + importClass);
                        continue;
                    }
                }
            }

            _package.addDependency(dependency);
        }
        return instance;
    }

}
