package com.bee32.icsf.access.acl;

import java.util.HashSet;
import java.util.Set;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.IPrincipal;

/**
 * Principal-ACL is the ACL subset related to a specific principal.
 */
public class PrincipalACL
        extends ArrayACL
        implements IPrincipalACL {

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
    public IPrincipal getPrincipal() {
        return principal;
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
    public Set<? extends IPrincipal> getDeclaredRelatedPrincipals() {
        HashSet<IPrincipal> wrapper = new HashSet<IPrincipal>();
        wrapper.add(principal);
        return wrapper;
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
    protected void onEntryChanged(Entry entry, boolean removed) {
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
