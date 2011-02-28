package com.bee32.icsf.access.builtins;

import java.util.Arrays;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.authority.IAuthority;

/**
 * @test {@link DividedPermissionTest}
 */
public abstract class DividedPermission
        extends Permission {

    private static final long serialVersionUID = 1L;

    private final DividedPermission parent;
    private final IAuthority authority;
    private final String name;
    private final String description;
    private final int[] ranges; // sorted.
//    private final boolean determined;

    public DividedPermission(IAuthority authority, String name, String description) {
        this(null, authority, name, description, new int[0]);
    }

    protected DividedPermission(DividedPermission parent, IAuthority authority, String name, String description,
            int... ranges) {
        if (authority == null)
            throw new NullPointerException("authority");
        if (name == null)
            throw new NullPointerException("name");
        if (ranges == null)
            throw new NullPointerException("ranges");
        this.parent = parent;
        this.authority = authority;
        this.name = name;
        this.description = description;
        this.ranges = Arrays.copyOf(ranges, ranges.length);
        Arrays.sort(this.ranges);
    }

    protected abstract DividedPermission newInstance(DividedPermission parent, String name, String description,
            int... ranges);

    protected abstract String getRangeName(int rangeId);

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public IAuthority getAuthority() {
        return authority;
    }

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
    public boolean equals(Object obj) {
        if (!(obj instanceof DividedPermission))
            return false;
        DividedPermission q = (DividedPermission) obj;
        if (!authority.equals(q.authority))
            return false;
        if (!name.equals(q))
            return false;
        if (parent != q.parent)
            if (parent == null || q.parent == null || !parent.equals(q.parent))
                return false;
        if (!Arrays.equals(ranges, q.ranges))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0x1c3ad4eb;
        hash += authority.hashCode();
        hash += name.hashCode() * 0x1d3c7e9a;
        if (parent != null)
            hash += parent.hashCode() * 0xfce2e570;
        hash += Arrays.hashCode(ranges);
        return hash;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer(name.length() + ranges.length * 16);
        buf.append(name);
        buf.append("(");
        for (int i = 0; i < ranges.length; i++) {
            if (i != 0)
                buf.append(", ");
            String rangeName = getRangeName(ranges[i]);
            if (rangeName == null)
                rangeName = "%" + ranges[i];
            buf.append(rangeName);
        }
        buf.append(")");
        return buf.toString();
    }

}
