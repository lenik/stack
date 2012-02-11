package com.bee32.icsf.access.acl;

import java.util.Collection;

import javax.free.UnexpectedException;

import org.hibernate.criterion.CriteriaSpecification;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.SinglePermissionBit;
import com.bee32.icsf.access.SinglePermissionTristate;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalCriteria;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.plover.ox1.c.CEntity;

public class ACLCriteria
        extends CriteriaSpec {

    /**
     * @param principal
     *            <code>null</code> for all principals.
     */
    @LeftHand(ACL.class)
    public static ICriteriaElement acls(Principal principal) {
        return compose(//
                alias("entries", "entry"), //
                PrincipalCriteria.inImSet("entry.principal", principal));
    }

    @LeftHand(ACL.class)
    public static ICriteriaElement acls(Collection<Integer> imSet) {
        return compose(//
                alias("entries", "entry"), //
                PrincipalCriteria.inImSet("entry.principal", imSet));
    }

    @LeftHand(CEntity.class)
    public static CriteriaElement aclWithin(Collection<Integer> aclIds) {
        if (aclIds == null)
            return null;
        else
            return or(isNull("aclId"), in("aclId", aclIds));
    }

    /**
     * @param principal
     *            <code>null</code> for all principals.
     */
    @LeftHand(Object[].class)
    public static ICriteriaElement distinctACLsImply(Principal principal, Permission permission) {
        return compose(//
                proj(distinct(property("id"))),//
                implies(principal, permission));
    }

    @LeftHand(Object[].class)
    public static ICriteriaElement distinctACLsImply(Collection<Integer> imSet, Permission permission) {
        return compose(//
                proj(distinct(property("id"))),//
                implies(imSet, permission));
    }

    /**
     * @param principal
     *            <code>null</code> for all principals.
     */
    @LeftHand(ACL.class)
    public static ICriteriaElement implies(Principal principal, Permission permission) {
        return compose( //
                alias("entries", "entry", CriteriaSpecification.LEFT_JOIN), //
                PrincipalCriteria.inImSet("entry.principal", principal), //
                implies("entry.", permission));
    }

    @LeftHand(ACL.class)
    public static ICriteriaElement implies(Collection<Integer> imSet, Permission permission) {
        return compose( //
                alias("entries", "entry", CriteriaSpecification.LEFT_JOIN), //
                PrincipalCriteria.inImSet("entry.principal", imSet), //
                implies("entry.", permission));
    }

    @LeftHand(ACLEntry.class)
    public static CriteriaElement implies(Permission permission) {
        return implies("", permission);
    }

    @LeftHand(ACLEntry.class)
    static CriteriaElement implies(String prefix, Permission permission) {
        if (permission == null)
            throw new NullPointerException("permission");

        // XXX 3-state booleans.

        CriteriaElement adminSpec = equals(prefix + "admin", true);
        CriteriaElement conj = conj(//
                implies(prefix + "usable", permission.getUse()), //
                implies(prefix + "readable", permission.getRead()), //
                implies(prefix + "writable", permission.getWrite()), //
                implies(prefix + "executable", permission.getExecute()), //
                implies(prefix + "creatable", permission.getCreate()), //
                implies(prefix + "deletable", permission.getDelete()) //
        );

        SinglePermissionBit ownBit = permission.getOwn();
        boolean adminWanted = ownBit.getTristate() == SinglePermissionTristate.allowed;
        if (adminWanted)
            return adminSpec; // and(adminSpec, conj);
        else
            return or(adminSpec, conj);
    }

    public static ICriteriaElement implies(String property, SinglePermissionBit bit) {
        return implies(property, bit, true);
    }

    public static CriteriaElement implies(String property, SinglePermissionBit bit, boolean strongImplication) {
        SinglePermissionTristate tristate = bit.getTristate();
        switch (tristate) {
        case allowed: // +/strong, 0
            if (strongImplication)
                return equals(property, true);
            else
                return or(equals(property, true), isNull(property));

        case inherited: // + 0
            return or(equals(property, true), isNull(property));

        case denied: // -1/strong
            if (strongImplication)
                return equals(property, false);
            else
                return null;
        default:
            throw new UnexpectedException("Bad tristate: " + tristate);
        }
    }

}
