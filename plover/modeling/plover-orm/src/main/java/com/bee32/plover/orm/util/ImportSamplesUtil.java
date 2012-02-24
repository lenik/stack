package com.bee32.plover.orm.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class ImportSamplesUtil {

    static Logger logger = LoggerFactory.getLogger(ImportSamplesUtil.class);

    static final Map<Class<?>, SamplePackage> beanMap = new HashMap<Class<?>, SamplePackage>();

    public static boolean register(SamplePackage contrib) {
        Class<?> clazz = contrib.getClass();

        SamplePackage existing = beanMap.get(clazz);
        if (existing != null)
            return false;

        beanMap.put(clazz, contrib);
        return true;
    }

    public static synchronized SamplePackage getInstance(Class<? extends SamplePackage> clazz) {
        if (clazz == null)
            throw new NullPointerException("clazz");

        SamplePackage instance = beanMap.get(clazz);
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

    /**
     * 将标准 {@link ImportSamples} 记入依赖项。
     *
     * 注：{@link ImportSamples} 仅在此处被分析并被转换为依赖。
     */
    public static void applyImports(SamplePackage contrib) {
        Class<?> clazz = contrib.getClass();

        ImportSamples imports = clazz.getAnnotation(ImportSamples.class);
        if (imports == null)
            return;

        for (Class<? extends SamplePackage> importClass : imports.value()) {
            SamplePackage importInstance = getInstance(importClass);
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
    public static Collection<SamplePackage> applyImports(ApplicationContext appctx) {
        Collection<SamplePackage> contribs = appctx.getBeansOfType(SamplePackage.class).values();
        for (SamplePackage contrib : contribs) {
            applyImports(contrib);
        }
        return contribs;
    }

}
