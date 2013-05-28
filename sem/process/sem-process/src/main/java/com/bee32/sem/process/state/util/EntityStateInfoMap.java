package com.bee32.sem.process.state.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import javax.free.ClassLocal;
import javax.free.UnexpectedException;
import javax.free.Utf8ResourceBundle;

public class EntityStateInfoMap {

    Map<Integer, StateIntInfo> map = new TreeMap<>();

    public Map<Integer, StateIntInfo> getMap() {
        return map;
    }

    public Set<Integer> getStateIntSet() {
        return map.keySet();
    }

    /**
     * @return <code>null</code> if the state int isn't defined.
     */
    public StateIntInfo getInfo(int stateIntValue) {
        return map.get(stateIntValue);
    }

    void putInfo(int value, StateIntInfo info) {
        if (info == null)
            throw new NullPointerException("info");
        map.put(value, info);
    }

    static ClassLocal<EntityStateInfoMap> all = new ClassLocal<>();

    public static EntityStateInfoMap getInstance(Class<?> clazz) {
        EntityStateInfoMap infoMap = all.get(clazz);
        if (infoMap == null) {
            infoMap = new EntityStateInfoMap();

            for (Field field : clazz.getFields()) {

                int modifiers = field.getModifiers();
                if (!Modifier.isPublic(modifiers) || !Modifier.isStatic(modifiers))
                    continue;

                StateInt _stateInt = field.getAnnotation(StateInt.class);
                if (_stateInt == null)
                    continue;

                try {
                    int intValue = (Integer) field.get(null);

                    String resourceName = clazz.getName().replace('.', '/');
                    StateIntInfo stateInfo = new StateIntInfo(intValue);

                    try {
                        ResourceBundle bundle = Utf8ResourceBundle.getBundle(resourceName, Locale.getDefault(),
                                clazz.getClassLoader());
                        String label = bundle.getString(field.getName() + ".label");
                        stateInfo.setLabel(label);

                        String description = bundle.getString(field.getName() + ".description");
                        stateInfo.setDescription(description);
                    } catch (MissingResourceException e) {
                        // show warning...dg
                    }

                    infoMap.putInfo(intValue, stateInfo);
                } catch (ReflectiveOperationException e) {
                    throw new UnexpectedException(e.getMessage(), e);
                }
            }
            all.put(clazz, infoMap);
        }
        return infoMap;
    }

}
