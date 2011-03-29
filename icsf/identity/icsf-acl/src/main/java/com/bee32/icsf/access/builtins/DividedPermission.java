package com.bee32.icsf.access.builtins;

import java.util.Arrays;

import javax.free.Nullables;

import com.bee32.icsf.access.Permission;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.EntityFormat;
import com.bee32.plover.util.PrettyPrintStream;

public abstract class DividedPermission
        extends Permission {

    private static final long serialVersionUID = 1L;

    private final DividedPermission parent;
    private final String name;
    private final String description;
    private final int[] ranges; // sorted.
// private final boolean determined;

    public DividedPermission(String name, String description) {
        this(null, name, description, new int[0]);
    }

    protected DividedPermission(DividedPermission parent, String name, String description, int... ranges) {
        if (name == null)
            throw new NullPointerException("name");
        if (ranges == null)
            throw new NullPointerException("ranges");
        this.parent = parent;
        this.name = name;
        this.description = description;
        this.ranges = Arrays.copyOf(ranges, ranges.length);
        Arrays.sort(this.ranges);
    }

    protected abstract DividedPermission newInstance(DividedPermission parent, String name, String description,
            int... ranges);

    protected abstract String getRangeName(int rangeId);

    public DividedPermission getParentPermission() {
        return parent;
    }

    @Override
    public boolean implies(Permission permission) {
        if (this.equals(permission))
            return true;
        if (permission instanceof DividedPermission) {
            DividedPermission q = (DividedPermission) permission;
            DividedPermission qParent = q.getParentPermission();
            boolean sibling = false;
            if (qParent == null) {
                if (implies(qParent))
                    return true;
                if (parent != null && parent.equals(qParent))
                    sibling = true;
            } else if (parent == null)
                sibling = true;

            if (sibling) {
                for (int range : q.ranges)
                    if (!containsRange(range))
                        return false;
                return true;
            }
        }
        return false;
    }

    protected boolean containsRange(int range) {
        int index = Arrays.binarySearch(ranges, range);
        return index >= 0;
    }

    public DividedPermission select(int... ranges) {
        int[] cross = new int[ranges.length];
        int len = 0;
        for (int range : ranges)
            if (containsRange(range))
                cross[len++] = range;
        cross = Arrays.copyOf(cross, len);
        return newInstance(this.parent, this.name, this.description, cross);
    }

    public DividedPermission divide(String subPermissionName, String description, int... ranges) {
        return newInstance(this, subPermissionName, description, ranges);
    }

    @Override
    protected boolean equalsEntity(EntityBean<Integer> entity) {
        DividedPermission other = (DividedPermission) entity;

        if (!Nullables.equals(parent, other.parent))
            return false;

        if (!Arrays.equals(ranges, other.ranges))
            return false;

        return true;
    }

    @Override
    protected int hashCodeEntity() {
        int hash = 0x1c3ad4eb;

        if (parent != null)
            hash += parent.hashCode();

        if (ranges != null)
            hash += Arrays.hashCode(ranges);

        return hash;
    }

    @Override
    public void toString(PrettyPrintStream out, EntityFormat format) {
        out.print(name);
        out.print("(");
        for (int i = 0; i < ranges.length; i++) {
            if (i != 0)
                out.print(", ");
            String rangeName = getRangeName(ranges[i]);
            if (rangeName == null)
                rangeName = "%" + ranges[i];
            out.print(rangeName);
        }
        out.print(")");
    }

}
