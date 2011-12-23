package com.bee32.plover.restful.resource;

import javax.free.ClassLocal;

public class PageDirectory {

    static ClassLocal<IObjectPageDirectory> classMap = new ClassLocal<IObjectPageDirectory>();

    public static void register(Class<?> clazz, IObjectPageDirectory pageDir) {
        classMap.put(clazz, pageDir);
    }

    public static void unregister(Class<?> clazz) {
        classMap.remove(clazz);
    }

    /**
     * Get the page directory for the specific object class.
     *
     * @return <code>null</code> if no page dir is registered for the specific class.
     */
    public static IObjectPageDirectory getPageDirectory(Class<?> clazz) {
        return getPageDirectory(clazz, false);
    }

    /**
     * Get the page directory for the specific object class.
     *
     * @param reusable
     *            If <code>true</code>, the page dir for "Shape" is usable for "Triangle".
     *            <code>false</code> otherwise.
     */
    public static IObjectPageDirectory getPageDirectory(Class<?> clazz, boolean reusable) {
        IObjectPageDirectory pageDir = classMap.get(clazz);
        if (pageDir != null)
            return pageDir;
        if (reusable) {
            Class<?> superclass = clazz.getSuperclass();
            if (superclass != null)
                return getPageDirectory(superclass, reusable);
        }
        return null;
    }

}
