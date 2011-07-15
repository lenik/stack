package com.bee32.plover.orm.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
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

    PersistenceUnit start;

    Map<PersistenceUnit, StdUnitPackage> convertedMap;
    {
        convertedMap = new IdentityHashMap<PersistenceUnit, StdUnitPackage>();
    }

    public StandardSamples() {
        super("std");
        start = CustomizedSessionFactoryBean.getForceUnit();
    }

    @Override
    protected void preamble() {
        convert(start);
    }

    SamplePackage convert(PersistenceUnit node) {
        StdUnitPackage pkg = convertedMap.get(node);
        if (pkg == null) {
            StdUnitPackage std = new StdUnitPackage(start);
            StdUnitPackage unit1 = convertedMap.put(node, std);
            for (ClassCatalog dependency : start.getDependencies()) {
                PersistenceUnit pu = (PersistenceUnit) dependency;
                pu = convert(node);
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

                instances.add(entity);
            }
        }

    }

}
