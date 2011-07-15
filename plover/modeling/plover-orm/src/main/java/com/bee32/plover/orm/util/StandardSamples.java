package com.bee32.plover.orm.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.ClassCatalog;
import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.unit.PersistenceUnit;

public class StandardSamples
        extends SampleContribution {

    static Logger logger = LoggerFactory.getLogger(StandardSamples.class);

    PersistenceUnit rootUnit;

    Map<PersistenceUnit, StdUnitPackage> convertedMap;
    {
        convertedMap = new IdentityHashMap<PersistenceUnit, StdUnitPackage>();
    }

    public StandardSamples() {
        super("std");
        rootUnit = CustomizedSessionFactoryBean.getForceUnit();
    }

    @Override
    protected void preamble() {
        StdUnitPackage rootPackage = convert(rootUnit);
        DiamondPackage.STANDARD.addDependency(rootPackage);
    }

    @Override
    public List<Entity<?>> getInstances() {
        return super.getInstances();
    }

    StdUnitPackage convert(PersistenceUnit node) {
        StdUnitPackage pkg = convertedMap.get(node);
        if (pkg == null) {
            pkg = new StdUnitPackage(node);
            convertedMap.put(node, pkg);
            for (ClassCatalog dependency : node.getDependencies()) {
                PersistenceUnit depUnit = (PersistenceUnit) dependency;
                StdUnitPackage depPkg = convert(depUnit);
                pkg.addDependency(depPkg);
            }
        }
        return pkg;
    }

    static class StdUnitPackage
            extends SamplePackage {

        public StdUnitPackage(PersistenceUnit unit) {
            super(unit.getName());
            for (Class<?> localClass : unit.getLocalClasses()) {
                scan(localClass);
            }
        }

        void scan(Class<?> declaringClass) {
            for (Field field : declaringClass.getDeclaredFields()) {

                int modifiers = field.getModifiers();
                if (!Modifier.isStatic(modifiers))
                    continue;

                Class<?> fieldType = field.getType();
                if (!Entity.class.isAssignableFrom(fieldType))
                    continue;

                if (!Modifier.isPublic(modifiers))
                    field.setAccessible(true);

                Entity<?> entity;
                try {
                    entity = (Entity<?>) field.get(null);
                } catch (ReflectiveOperationException e) {
                    logger.warn("Can't get static field " + field);
                    continue;
                }

                addInstance(entity);
            }
        }

    }

}
