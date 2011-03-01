package com.bee32.icsf.principal;

import java.util.Iterator;

public abstract class AbstractUser
        extends AbstractPrincipal
        implements IUserPrincipal {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     * <p>
     * In the default implementation, the first role in {@link #getAssignedRoles()} is returned.
     */
    @Override
    public IRolePrincipal getPrimaryRole() {
        Iterator<? extends IRolePrincipal> roles = getAssignedRoles().iterator();
        if (roles.hasNext())
            return roles.next();
        else
            return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * In the default implementation, the first group in {@link #getAssignedRoles()} is returned.
     */
    @Override
    public IGroupPrincipal getPrimaryGroup() {
        Iterator<? extends IGroupPrincipal> groups = getAssignedGroups().iterator();
        if (groups.hasNext())
            return groups.next();
        else
            return null;
    }

    @Override
    public boolean implies(IPrincipal principal) {
        if (principal == null)
            throw new NullPointerException("principal");

        if (this.equals(principal))
            return true;

        for (IGroupPrincipal group : getAssignedGroups())
            if (group.implies(principal))
                return true;

        for (IRolePrincipal role : getAssignedRoles())
            if (role.implies(principal))
                return true;

        return false;
    }

    @Override
    public void accept(IPrincipalVisitor visitor) {
        if (visitor.startPrincipal(this)) {
            for (IGroupPrincipal group : getAssignedGroups())
                visitor.visitImplication(group);
            for (IRolePrincipal role : getAssignedRoles())
                visitor.visitImplication(role);
            visitor.endPrincipal(this);
        }
    }

}
