package com.bee32.plover.arch.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.free.ClassLocal;

import com.bee32.plover.arch.util.dto.FieldPropertyAccessor;
import com.bee32.plover.arch.util.dto.IPropertyAccessor;

public class ReflectLocks {

    static ClassLocal<ClassLocking> classLockingMap = new ClassLocal<>();

    public static boolean isLocked(Object obj) {
        ClassLocking locking = getClassLocking(obj.getClass());
        while (locking != null) {
            for (IPropertyAccessor<? extends ILockable> lockableProperty : locking.getLockableProperties()) {
                ILockable lockable = lockableProperty.get(obj);
                if (lockable != null && lockable.isLocked())
                    return true;
            }
            locking = locking.getParent();
        }
        return false;
    }

    public static ClassLocking getClassLocking(Class<?> clazz) {
        ClassLocking classLocking = classLockingMap.get(clazz);
        if (classLocking == null) {
            classLocking = analyze(clazz);
            classLockingMap.put(clazz, classLocking);
        }
        return classLocking;
    }

    public static ClassLocking analyze(Class<?> clazz) {
        Class<?> superclass = clazz.getSuperclass();
        ClassLocking superLocking = null;
        if (superclass != null)
            superLocking = getClassLocking(superclass).reduce();

        ClassLocking locking = new ClassLocking(clazz, superLocking);
        List<IPropertyAccessor<? extends ILockable>> lockableProperties = locking.getLockableProperties();

        for (Field field : clazz.getDeclaredFields()) {
            if (ILockable.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);

                @SuppressWarnings("unchecked")
                IPropertyAccessor<? extends ILockable> lockableProperty //
                = (IPropertyAccessor<? extends ILockable>) new FieldPropertyAccessor(field);

                lockableProperties.add(lockableProperty);
            }
        }
        return locking;
    }

}

class ClassLocking {

    final Class<?> clazz;
    final ClassLocking parent;
    final List<IPropertyAccessor<? extends ILockable>> lockableProperties;

    public ClassLocking(Class<?> clazz, ClassLocking parent) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        this.clazz = clazz;
        this.parent = parent;
        this.lockableProperties = new ArrayList<>();
    }

    public ClassLocking reduce() {
        if (lockableProperties.isEmpty())
            if (parent == null)
                return null;
            else
                return parent.reduce();
        else
            return this;
    }

    public ClassLocking getParent() {
        return parent;
    }

    public List<IPropertyAccessor<? extends ILockable>> getLockableProperties() {
        return lockableProperties;
    }

}