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
    public abstract Set<? extends IGroupPrincipal> getAssignedGroups();

    @SuppressWarnings("unchecked")
    @Override
    public boolean addAssignedGroup(IGroupPrincipal group) {
        if (group == null)
            throw new NullPointerException("group");

        Set<IGroupPrincipal> assignedGroups = (Set<IGroupPrincipal>) getAssignedGroups();
        if (!assignedGroups.add(group))
            return false;

        group.addMemberUser(this);
        return true;
    }

    @Override
    public boolean removeAssignedGroup(IGroupPrincipal group) {
        if (group == null)
            throw new NullPointerException("group");

        if (!group.removeMemberUser(this))
            return false;

        if (getAssignedGroups().remove(group))
            group.removeMemberUser(this);

        return true;
    }

    @Override
    public abstract Set<? extends IRolePrincipal> getAssignedRoles();

    @SuppressWarnings("unchecked")
    @Override
    public boolean addAssignedRole(IRolePrincipal role) {
        if (role == null)
            throw new NullPointerException("role");

        Set<IRolePrincipal> assignedRoles = (Set<IRolePrincipal>) getAssignedRoles();
        if (!assignedRoles.add(role))
            return false;

        role.removeResponsibleUser(this);
        return true;
    }

    @Override
    public boolean removeAssignedRole(IRolePrincipal role) {
        if (role == null)
            throw new NullPointerException("role");

        if (!getAssignedRoles().remove(role))
            return false;

        role.removeResponsibleUser(this);
        return true;
    }

    @Override
    public AbstractUser detach() {
        super.detach();

        for (IGroupPrincipal group : flyOver(getAssignedGroups()))
            group.removeMemberUser(this);

        for (IRolePrincipal role : flyOver(getAssignedRoles()))
            role.removeResponsibleUser(this);

        return this;
    }

    @Override
    public boolean implies(IPrincipal principal) {
        if (super.implies(principal))
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
