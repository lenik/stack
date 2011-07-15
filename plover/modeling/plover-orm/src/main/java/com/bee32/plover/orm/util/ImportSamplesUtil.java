package com.bee32.plover.orm.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class ImportSamplesUtil {

    static Logger logger = LoggerFactory.getLogger(ImportSamplesUtil.class);

    static final Map<Class<?>, SampleContribution> beanMap = new HashMap<Class<?>, SampleContribution>();

    public static boolean register(SampleContribution contrib) {
        Class<?> clazz = contrib.getClass();

        SampleContribution existing = beanMap.get(clazz);
        if (existing != null)
            return false;

        beanMap.put(clazz, contrib);
        return true;
    }

    public static synchronized SampleContribution getInstance(Class<? extends SampleContribution> clazz) {
        if (clazz == null)
            throw new NullPointerException("clazz");

        SampleContribution instance = beanMap.get(clazz);
        if (instance == null) {
            // lazy-create
            try {
                instance = clazz.newInstance();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e.getMessage(), e);
            }

            register(instance);
        }
        return instance;
    }

    public static void applyImports(SampleContribution contrib) {
        Class<?> clazz = contrib.getClass();

        ImportSamples imports = clazz.getAnnotation(ImportSamples.class);
        if (imports == null)
            return;

        for (Class<? extends SampleContribution> importClass : imports.value()) {
            SampleContribution importInstance = getInstance(importClass);
            if (importInstance == null) {
                logger.warn("Samples contribution " + contrib + " imports non-existing contribution " + importClass);
                continue;
            }

            contrib.addDependency(importInstance);
        }
    }

    /**
     * Find all sample contributions and analyze their dependencies.
     *
     * @return All sample contributions with import converted to dependencies.
     */
    public static Collection<SampleContribution> applyImports(ApplicationContext appctx) {
        Collection<SampleContribution> contribs = appctx.getBeansOfType(SampleContribution.class).values();
        for (SampleContribution contrib : contribs) {
            applyImports(contrib);
        }
        return contribs;
    }

}
