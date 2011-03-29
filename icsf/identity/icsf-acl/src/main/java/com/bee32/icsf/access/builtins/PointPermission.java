package com.bee32.icsf.access.builtins;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.bee32.icsf.access.Permission;

public final class PointPermission
        extends Permission {

    private static final long serialVersionUID = 1L;

    PointPermission(String name) {
        super(name);
    }

    // -o accesscontrol.Permission

    /**
     * A point permission only implies itself.
     */
    @Override
    public boolean implies(Permission permission) {
        return permission == this;
    }

    /**
     * Unique point-permission registry
     */
    private static final Map<String, PointPermission> pointMap = new HashMap<String, PointPermission>();

    /**
     * Create a new point permission for the given name.
     *
     * @return Non-<code>null</code> new created point permission instance.
     * @throws IllegalStateException
     *             if the point permission with the given is already existed.
     */
    public static synchronized PointPermission create(String pointName) {
        if (pointMap.containsKey(pointName))
            throw new IllegalStateException("PointPermission " + pointName + " is already defined");

        PointPermission pointPermission = new PointPermission(pointName);
        pointMap.put(pointName, pointPermission);
        return pointPermission;
    }

    /**
     * Get the named PointPermission.
     * <p>
     * If the PointPermission of the specified point name is existed, it's returned.
     *
     * Otherwise, a new PointPermission is created if <code>createIfNotExisted</code> parameter is
     * <code>true</code>.
     *
     * @param pointName
     *            Non-<code>null</code> name of the point permission.
     * @param createIfNotExisted
     *            Whether to create a new PointPermission if the specified point name doesn't exist.
     * @return The PointPermission instance for the given name. <code>null</code> if not existed and
     *         parameter <code>createIfNotExisted</code> is <code>false</code>.
     */
    public static PointPermission getInstance(String pointName, boolean createIfNotExisted) {
        PointPermission pointPermission = pointMap.get(pointName);
        if (pointPermission == null && createIfNotExisted) {
            synchronized (PointPermission.class) {
                pointPermission = pointMap.get(pointName);
                if (pointPermission == null) {
                    pointPermission = new PointPermission(pointName);
                    pointMap.put(pointName, pointPermission);
                }
            }
        }
        return pointPermission;
    }

    /**
     * Get the named PointPermission.
     * <p>
     * If the PointPermission of the specified point name is existed, it's returned.
     *
     * Otherwise, returns <code>null</code>.
     *
     * @param pointName
     *            Non-<code>null</code> name of the point permission.
     * @return The PointPermission instance for the given name. Returns <code>null</code> if not
     *         existed.
     */
    public static PointPermission getInstance(String pointName) {
        return getInstance(pointName, false);
    }

    /**
     * Register a point permission.
     * <p>
     * By register a pre-existing point permission, you can prepare the appearance of a point
     * permission yourself.
     *
     * @param parsedPointPermission
     *            The prepared point permission to be registered.
     * @throws IllegalStateException
     *             If any point permission with the same point name is already existed.
     */
    public static void register(PointPermission parsedPointPermission)
            throws IllegalStateException {
        String pointName = parsedPointPermission.getName();

        PointPermission existing = pointMap.get(pointName);
        if (existing != null)
            throw new IllegalStateException("PointPermission " + pointName + " is already defined.");

        pointMap.put(pointName, parsedPointPermission);
    }

    public static Collection<PointPermission> getAllInstances() {
        return pointMap.values();
    }

}
