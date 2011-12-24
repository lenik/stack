package com.bee32.plover.restful.resource;

import javax.free.ClassLocal;
import javax.free.PrefixMap;

import com.bee32.plover.rtx.location.Location;

public class PageDirectory {

    static ClassLocal<IObjectPageDirectory> classMap = new ClassLocal<IObjectPageDirectory>();
    static PrefixMap<Class<?>> basePathReverseMap = new PrefixMap<Class<?>>();

    public static void register(Class<?> clazz, IObjectPageDirectory pageDir) {
        classMap.put(clazz, pageDir);
        Location baseLocation = pageDir.getBaseLocation();
        String basePath = baseLocation.getBase() + "/";
        basePathReverseMap.put(basePath, clazz);
    }

    public static boolean unregister(Class<?> clazz) {
        IObjectPageDirectory pageDir = classMap.remove(clazz);
        if (pageDir == null)
            return false;
        Location baseLocation = pageDir.getBaseLocation();
        String basePath = baseLocation.getBase() + "/";
        basePathReverseMap.remove(basePath);
        return true;
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

    /**
     * Find the most exactly matched entry registered with any prefix of the given path.
     *
     * @return <code>null</code> if no matching entry registered.
     */
    public static Class<?> whichClass(String path) {
        Class<?> clazz = basePathReverseMap.floor(path);
        return clazz;
    }

}
