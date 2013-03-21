package com.bee32.plover.model.state;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatePropertyFlagsMapMap {

    static final Logger logger = LoggerFactory.getLogger(StatePropertyFlagsMapMap.class);

    private Map<Integer, PropetyFlagsMap> stateMap = new HashMap<>();

    public void readPropertyAnnotations(Class<?> clazz)
            throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

        for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
            String propertyName = property.getName();
            Method getter = property.getReadMethod();

            LockedStates lockedStates = getter.getAnnotation(LockedStates.class);
            if (lockedStates != null) {
                for (int state : lockedStates.value()) {
                    PropetyFlagsMap pfm = getPropertyFlagsMap(state);
                    PropertyFlags flags = pfm.resolve(propertyName);
                    flags.setLocked(true);
                }
            }

            InvisibleStates invisibleStates = getter.getAnnotation(InvisibleStates.class);
            if (invisibleStates != null) {
                for (int state : invisibleStates.values()) {
                    PropetyFlagsMap pfm = getPropertyFlagsMap(state);
                    PropertyFlags flags = pfm.resolve(propertyName);
                    flags.setVisible(false);
                }
            }
        }
    }

    public PropetyFlagsMap getPropertyFlagsMap(int state) {
        PropetyFlagsMap pfm = stateMap.get(state);
        if (pfm == null) {
            pfm = new PropetyFlagsMap();
            stateMap.put(state, pfm);
        }
        return pfm;
    }

    static final Map<Class<?>, StatePropertyFlagsMapMap> classMap;
    static {
        classMap = new HashMap<>();
    }

    public static synchronized StatePropertyFlagsMapMap forClass(Class<?> clazz) {
        StatePropertyFlagsMapMap pfmm = classMap.get(clazz);
        if (pfmm == null) {
            pfmm = new StatePropertyFlagsMapMap();
            try {
                pfmm.readPropertyAnnotations(clazz);
            } catch (IntrospectionException e) {
                logger.error("Failed to read property annotations", e);
            }
            classMap.put(clazz, pfmm);
        }
        return pfmm;
    }

}
