package com.bee32.icsf.principal;

import java.util.Iterator;
import java.util.Set;

public abstract class AbstractUser
        extends Principal
        implements IUserPrincipal {

    private static final long serialVersionUID = 1L;

    public AbstractUser() {
        super();
    }

    public AbstractUser(String name) {
        super(name);
    }

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
    public abstract Set<IGroupPrincipal> getAssignedGroups() ;

    @Override
    public void addAssignedGroup(IGroupPrincipal group) {
        getAssignedGroups().add(group);
    }

    @Override
    public void removeAssignedGroup(IGroupPrincipal group) {
        getAssignedGroups().remove(group);
    }

    @Override
    public abstract Set<IRolePrincipal> getAssignedRoles();

    @Override
    public void addAssignedRole(IRolePrincipal role) {
        getAssignedRoles().add(role);
    }

    @Override
    public void removeAssignedRole(IRolePrincipal role) {
        getAssignedRoles().remove(role);
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
