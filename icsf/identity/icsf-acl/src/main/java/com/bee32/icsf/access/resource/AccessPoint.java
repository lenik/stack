package com.bee32.icsf.access.resource;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.bee32.plover.arch.util.ClassUtil;

/**
 * 访问点
 *
 * 相关服务的访问控制点
 *
 * <p lang="en">
 * Access Point
 *
 * Access control point for related service.
 */
public final class AccessPoint
        extends Resource {

    AccessPoint(String name) {
        super(name);
    }

    /**
     * Unique named point registry
     */
    private static final Map<String, AccessPoint> pointMap = new HashMap<String, AccessPoint>();

    /**
     * Create a new access point for the given name.
     *
     * @return Non-<code>null</code> new created point permission instance.
     * @throws IllegalStateException
     *             if the point permission with the given is already existed.
     */
    public static synchronized AccessPoint create(String pointName) {
        AccessPoint existing = pointMap.get(pointName);
        if (existing != null)
            throw new IllegalStateException("Point " + pointName + " is already registered.");

        AccessPoint point = new AccessPoint(pointName);
        pointMap.put(pointName, point);

        return point;
    }

    /**
     * Get the named access point.
     * <p>
     * If the AccessPoint of the specified point name is existed, it's returned.
     *
     * Otherwise, a new AccessPoint is created if <code>createIfNotExisted</code> parameter is
     * <code>true</code>.
     *
     * @param pointName
     *            Non-<code>null</code> name of the point permission.
     * @param createIfNotExisted
     *            Whether to create a new AccessPoint if the specified point name doesn't exist.
     * @return The AccessPoint instance for the given name. <code>null</code> if not existed and
     *         parameter <code>createIfNotExisted</code> is <code>false</code>.
     */
    public static AccessPoint getInstance(String pointName, boolean createIfNotExisted) {
        AccessPoint point = pointMap.get(pointName);
        if (point == null && createIfNotExisted) {
            synchronized (AccessPoint.class) {
                point = pointMap.get(pointName);
                if (point == null) {
                    point = create(pointName);
                }
            }
        }
        return point;
    }

    /**
     * Get the named AccessPoint.
     * <p>
     * If the AccessPoint of the specified point name is existed, it's returned.
     *
     * Otherwise, returns <code>null</code>.
     *
     * @param pointName
     *            Non-<code>null</code> name of the point permission.
     * @return The AccessPoint instance for the given name. Returns <code>null</code> if not
     *         existed.
     */
    public static AccessPoint getInstance(String pointName) {
        return getInstance(pointName, false);
    }

    /**
     * Register a point permission.
     * <p>
     * By register a pre-existing point permission, you can prepare the appearance of a point
     * permission yourself.
     *
     * @param parsedPoint
     *            The prepared point permission to be registered.
     * @throws IllegalStateException
     *             If any point permission with the same point name is already existed.
     */
    public static void register(AccessPoint parsedPoint)
            throws IllegalStateException {
        String pointName = parsedPoint.getName();
        pointMap.put(pointName, parsedPoint);
    }

    public static Collection<AccessPoint> getInstances() {
        return pointMap.values();
    }

    public static void main(String[] args) {
        Locale locale = Locale.forLanguageTag("en");
        Locale.setDefault(locale);
        String name = ClassUtil.getTypeName(AccessPoint.class);
        System.out.println(name);
    }

}
