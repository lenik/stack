package com.bee32.plover.inject.util;

import javax.free.PreorderMap;

public class NameQualifiedClassMap<T>
        extends PreorderMap<NameQualifiedClass, T> {

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

    public T floor(Class<?> clazz) {
        return floor(clazz, null);
    }

    public T floor(Class<?> clazz, String qualifier) {
        NameQualifiedClass nqc = new NameQualifiedClass(clazz, qualifier);
        return super.floor(nqc);
    }

    public void put(Class<?> clazz, T value) {
        put(clazz, null, value);
    }

    public void put(Class<?> clazz, String qualifier, T value) {
        NameQualifiedClass nqc = new NameQualifiedClass(clazz, qualifier);
        super.put(nqc, value);
    }

    public T remove(Class<?> clazz) {
        return remove(clazz, null);
    }

    public T remove(Class<?> clazz, String qualifier) {
        NameQualifiedClass nqc = new NameQualifiedClass(clazz, qualifier);
        return super.remove(nqc);
    }

}
