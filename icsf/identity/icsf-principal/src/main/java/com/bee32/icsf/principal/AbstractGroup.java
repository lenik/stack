package com.bee32.icsf.principal;

import java.util.Iterator;

public abstract class AbstractGroup
        extends AbstractPrincipal
        implements IGroupPrincipal {

    private static final long serialVersionUID = 1L;

    @Override
    public IRolePrincipal getPrimaryRole() {
        Iterator<? extends IRolePrincipal> roles = getAssignedRoles().iterator();
        if (roles.hasNext())
            return roles.next();
        else
            return null;
    }

    @Override
    public IUserPrincipal getOwner() {
        return null;
    }

    @Override
    public boolean implies(IPrincipal principal) {
        if (principal == null)
            throw new NullPointerException("principal");

        if (this.equals(principal))
            return true;

        IGroupPrincipal base = getInheritedGroup();
        if (base != null)
            if (base.implies(principal))
                return true;

        for (IRolePrincipal role : getAssignedRoles())
            if (role.implies(principal))
                return true;

        return false;
    }

    @Override
    public void accept(IPrincipalVisitor visitor) {
        if (visitor.startPrincipal(this)) {
            IGroupPrincipal base = getInheritedGroup();
            if (base != null)
                visitor.visitImplication(base);
            for (IRolePrincipal role : getAssignedRoles())
                visitor.visitImplication(role);
            visitor.endPrincipal(this);
        }
    }

}
