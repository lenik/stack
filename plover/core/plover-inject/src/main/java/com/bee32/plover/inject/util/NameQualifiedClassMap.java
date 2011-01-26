package com.bee32.plover.inject.util;

import javax.free.PreorderMap;

public class NameQualifiedClassMap
        extends PreorderMap<NameQualifiedClass, Object> {

    private static final long serialVersionUID = 1L;

    public NameQualifiedClassMap() {
        super(NameQualifiedClassPreorder.getInstance());
    }

    public boolean containsKey(Class<?> clazz) {
        return containsKey(clazz, null);
    }

    public boolean containsKey(Class<?> clazz, String qualifier) {
        NameQualifiedClass nqc = new NameQualifiedClass(clazz, qualifier);
        return containsKey(nqc);
    }

    public Object floor(Class<?> clazz) {
        return floor(clazz, null);
    }

    public Object floor(Class<?> clazz, String qualifier) {
        NameQualifiedClass nqc = new NameQualifiedClass(clazz, qualifier);
        return super.floor(nqc);
    }

    public void put(Class<?> clazz, Object value) {
        put(clazz, null, value);
    }

    public void put(Class<?> clazz, String qualifier, Object value) {
        NameQualifiedClass nqc = new NameQualifiedClass(clazz, qualifier);
        super.put(nqc, value);
    }

    public Object remove(Class<?> clazz) {
        return remove(clazz, null);
    }

    public Object remove(Class<?> clazz, String qualifier) {
        NameQualifiedClass nqc = new NameQualifiedClass(clazz, qualifier);
        return super.remove(nqc);
    }

}
