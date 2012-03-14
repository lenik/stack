package com.bee32.plover.orm.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bee32.plover.arch.util.ICloneable;
import com.bee32.plover.arch.util.ICopyable;
import com.bee32.plover.arch.util.IEnclosedObject;

public class CopyUtils {

    public static <T extends ICloneable> T clone(T source) {
        if (source == null)
            return null;
        T cloned = (T) source.clone();
        return cloned;
    }

    public static <T extends ICopyable> T copy(T source) {
        return copy(source, null);
    }

    public static <T extends ICopyable> T copy(T source, Object enclosingObject) {
        if (source == null)
            return null;
        T copy = (T) source.copy();
        if (enclosingObject != null)
            applyEnclosingObject(copy, enclosingObject);
        return copy;
    }

    public static <T extends ICopyable> Set<T> copySet(Set<T> list) {
        return copySet(list, null);
    }

    public static <T extends ICopyable> Set<T> copySet(Set<T> list, Object enclosingObject) {
        Set<T> newSet = new HashSet<T>();
        for (T element : list) {
            T copy = (T) element.copy();
            newSet.add(copy);
            if (enclosingObject != null)
                applyEnclosingObject(copy, enclosingObject);
        }
        return newSet;
    }

    public static <T extends ICopyable> List<T> copyList(List<T> list) {
        return copyList(list, null);
    }

    public static <T extends ICopyable> List<T> copyList(List<T> list, Object enclosingObject) {
        List<T> newList = new ArrayList<T>();
        for (T element : list) {
            T copy = (T) element.copy();
            newList.add(copy);
            if (enclosingObject != null)
                applyEnclosingObject(copy, enclosingObject);
        }
        return newList;
    }

    static void applyEnclosingObject(Object obj, Object enclosingObject) {
        if (obj instanceof IEnclosedObject<?>) {
            @SuppressWarnings("unchecked")
            IEnclosedObject<Object> enclosed = (IEnclosedObject<Object>) obj;
            enclosed.setEnclosingObject(enclosingObject);
        }
    }

}
