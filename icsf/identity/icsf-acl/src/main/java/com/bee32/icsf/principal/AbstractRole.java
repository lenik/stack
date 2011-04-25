package com.bee32.icsf.principal;

import java.util.Set;

public abstract class AbstractRole
        extends Principal
        implements IRolePrincipal {

    private static final long serialVersionUID = 1L;

    public AbstractRole() {
        super();
    }

    public AbstractRole(String name) {
        super(name);
    }

    @Override
    public abstract Set<? extends IUserPrincipal> getResponsibleUsers();

    @SuppressWarnings("unchecked")
    @Override
    public boolean addResponsibleUser(IUserPrincipal user) {
        if (user == null)
            throw new NullPointerException("user");

        Set<IUserPrincipal> responsibleUsers = (Set<IUserPrincipal>) getResponsibleUsers();
        if (!responsibleUsers.add(user))
            return false;

        user.addAssignedRole(this);
        return true;
    }

    @Override
    public boolean removeResponsibleUser(IUserPrincipal user) {
        if (user == null)
            throw new NullPointerException("user");

        if (!getResponsibleUsers().remove(user))
            return false;

        user.removeAssignedRole(this);
        return true;
    }

    @Override
    public abstract Set<? extends IGroupPrincipal> getResponsibleGroups();

    @SuppressWarnings("unchecked")
    @Override
    public boolean addResponsibleGroup(IGroupPrincipal group) {
        if (group == null)
            throw new NullPointerException("group");

        Set<IGroupPrincipal> responsibleGroups = (Set<IGroupPrincipal>) getResponsibleGroups();
        if (!responsibleGroups.add(group))
            return false;

        group.addAssignedRole(this);
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean removeResponsibleGroup(IGroupPrincipal group) {
        if (group == null)
            throw new NullPointerException("group");

        Set<IGroupPrincipal> responsibleGroups = (Set<IGroupPrincipal>) getResponsibleGroups();
        if (!responsibleGroups.remove(group))
            return false;

        group.removeAssignedRole(this);
        return true;
    }

    @Override
    public AbstractRole detach() {
        super.detach();

        for (IGroupPrincipal group : flyOver(getResponsibleGroups()))
            group.removeAssignedRole(this);

        for (IUserPrincipal user : flyOver(getResponsibleUsers()))
            user.removeAssignedRole(this);

        return this;
    }

    @Override
    public boolean implies(IPrincipal principal) {
        if (super.implies(principal))
            return true;

        IRolePrincipal base = getInheritedRole();
        if (base != null)
            if (base.implies(principal))
                return true;

        return false;
    }

    @Override
    public void accept(IPrincipalVisitor visitor) {
        if (visitor.startPrincipal(this)) {
            IRolePrincipal base = getInheritedRole();
            if (base != null)
                visitor.visitImplication(base);
            visitor.endPrincipal(this);
        }
    }

}
