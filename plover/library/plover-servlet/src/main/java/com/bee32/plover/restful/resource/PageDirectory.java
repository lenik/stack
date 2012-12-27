package com.bee32.plover.restful.resource;

import javax.free.ClassLocal;
import javax.free.PrefixMap;

import com.bee32.plover.arch.generic.IParameterized;
import com.bee32.plover.arch.generic.IParameterizedType;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.rtx.location.Location;

public class PageDirectory {

    static ClassLocal<IObjectPageDirectory> classMap = new ClassLocal<IObjectPageDirectory>();
    static PrefixMap<Class<?>> basePathReverseMap = new PrefixMap<Class<?>>();

    public static void register(Class<?> clazz, IObjectPageDirectory pageDir) {
        // TODO - merge page dirs for the same class?
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
     * @param inheritable
     *            If <code>true</code>, the page dir for "Shape" is usable for "Triangle".
     *            <code>false</code> otherwise.
     */
    public static IObjectPageDirectory getPageDirectory(Class<?> clazz, boolean inheritable) {
        clazz = ClassUtil.skipProxies(clazz);
        IObjectPageDirectory pageDir = classMap.get(clazz);
        if (pageDir != null)
            return pageDir;
        if (inheritable) {
            Class<?> superclass = clazz.getSuperclass();
            if (superclass != null)
                return getPageDirectory(superclass, inheritable);
        }
        return null;
    }

    public static IObjectPageDirectory getPageDirectory(Object obj) {
        return getPageDirectory(obj, false);
    }

    public static IObjectPageDirectory getPageDirectory(Object obj, boolean inheritable) {
        if (obj == null)
            throw new NullPointerException("obj");
        if (obj instanceof IParameterized) {
            IParameterized parameterized = (IParameterized) obj;
            IParameterizedType type = parameterized.getParameterizedType();
            IObjectPageDirectory pageDir = type.getFeature(obj, IObjectPageDirectory.class);
            if (pageDir != null)
                return pageDir;
        }
        return getPageDirectory(obj.getClass(), inheritable);
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
