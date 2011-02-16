package com.bee32.sems.security.access.acl;

import java.util.Arrays;
import java.util.Collection;

import com.bee32.sems.security.access.IPrincipal;
import com.bee32.sems.security.access.Permission;

public class PrincipalACL
        extends ArrayACL {

    private final IACL outer;
    private final IPrincipal principal;

    private IACL inheritedPrincipalACL;

    public PrincipalACL(IACL outer, IPrincipal principal) {
        if (outer == null)
            throw new NullPointerException("outer");
        if (principal == null)
            throw new NullPointerException("principal");
        this.outer = outer;
        this.principal = principal;
    }

    @Override
    public IACL getInheritedACL() {
        if (inheritedPrincipalACL == null) {
            IACL outerParent = outer.getInheritedACL();
            if (outerParent != null)
                inheritedPrincipalACL = outerParent.select(principal);
        }
        return inheritedPrincipalACL;
    }

    @Override
    public Collection<? extends IPrincipal> getDeclaredPrincipals() {
        return Arrays.asList(new IPrincipal[] { this.principal });
    }

    @Override
    public IACLPolicy getACLPolicy() {
        return outer.getACLPolicy();
    }

    @Override
    protected IACL newACLRange() {
        return new PrincipalACL(outer, principal);
    }

    @Override
    public IACL select(IPrincipal selectPrincipal) {
        if (selectPrincipal == null)
            throw new NullPointerException("selectPrincipal");
        if (selectPrincipal.implies(this.principal))
            return this;
        else
            return EMPTY;
    }

    @Override
    public IACL select(IPrincipal selectPrincipal, Permission selectPermission) {
        if (selectPrincipal == null)
            throw new NullPointerException("selectPrincipal");
        if (selectPrincipal.implies(this.principal)) {
            return select(selectPermission);
        } else
            return EMPTY;
    }

}
