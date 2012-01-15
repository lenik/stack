package com.bee32.icsf.access;

import java.io.Serializable;

/**
 * <table>
 * <tr>
 * <th>name</th>
 * <th>allow</th>
 * <th>deny</th>
 * </tr>
 * <tr>
 * <td>allow, default</td>
 * <td>-</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>deny</td>
 * <td>-</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td>allow</td>
 * <td>1</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>deny, deny-overrided</td>
 * <td>1</td>
 * <td>1</td>
 * </tr>
 * </table>
 */
public final class SinglePermissionBit
        implements Serializable {

    private static final long serialVersionUID = 1L;

    Permission perm;
    int mask;

    public SinglePermissionBit(Permission permission, int mask) {
        this.perm = permission;
        this.mask = mask;
    }

    public boolean isAllowed() {
        return (perm.denyBits & mask) == 0;
    }

    public void setAllowed(boolean allowed) {
        perm.denyBits &= ~mask;
        if (allowed)
            perm.allowBits |= mask;
        else
            perm.allowBits &= ~mask;
    }

    public boolean isDenied() {
        return (perm.denyBits & mask) == mask;
    }

    public void setDenied(boolean denied) {
        perm.allowBits &= ~mask;
        if (denied)
            perm.denyBits |= mask;
        else
            perm.denyBits &= ~mask;
    }

    public boolean isInherited() {
        return (perm.allowBits & mask) == 0 && (perm.denyBits & mask) == 0;
    }

    public void setInherited(boolean inherited) {
        perm.denyBits &= ~mask;
        if (inherited)
            perm.allowBits &= ~mask;
        else
            perm.allowBits |= mask;
    }

    public boolean isDenyOverrided() {
        return (perm.allowBits & mask) == mask && (perm.denyBits & mask) == mask;
    }

    public void setDenyOverrided(boolean denyOverrided) {
        perm.denyBits |= mask;
        if (denyOverrided)
            perm.allowBits |= mask;
        else
            perm.allowBits &= ~mask;
    }

    public SinglePermissionTristate getTristate() {
        if ((perm.denyBits & mask) == mask)
            return SinglePermissionTristate.denied;
        if ((perm.allowBits & mask) == mask)
            return SinglePermissionTristate.allowed;
        return SinglePermissionTristate.inherited;
    }

    public void setTristateBoolean(SinglePermissionTristate tristate) {
        if (tristate == SinglePermissionTristate.inherited) {
            perm.allowBits &= ~mask;
            perm.denyBits &= ~mask;
        } else if (tristate == SinglePermissionTristate.allowed) {
            perm.allowBits |= mask;
            perm.denyBits &= ~mask;
        } else {
            perm.allowBits &= ~mask;
            perm.denyBits |= mask;
        }
    }

    public Boolean getTristateBoolean() {
        return getTristate().getBoolean();
    }

    public void setTristateBoolean(Boolean tristateBoolean) {
        if (tristateBoolean == Boolean.TRUE)
            setTristateBoolean(SinglePermissionTristate.allowed);
        else if (tristateBoolean == Boolean.FALSE)
            setTristateBoolean(SinglePermissionTristate.denied);
        else
            setTristateBoolean(SinglePermissionTristate.inherited);
    }

}
