package com.bee32.icsf.principal;

import java.util.Iterator;
import java.util.Set;

public abstract class AbstractGroup
        extends Principal
        implements IGroupPrincipal {

    private static final long serialVersionUID = 1L;

    public AbstractGroup() {
        super();
    }

    public AbstractGroup(String name) {
        super(name);
    }

    @Override
    public IRolePrincipal getPrimaryRole() {
        Iterator<? extends IRolePrincipal> roles = getAssignedRoles().iterator();
        if (roles.hasNext())
            return roles.next();
        else
            return null;
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

        role.removeResponsibleGroup(this);
        return true;
    }

    @Override
    public boolean removeAssignedRole(IRolePrincipal role) {
        if (role == null)
            throw new NullPointerException("role");

        if (!getAssignedRoles().remove(role))
            return false;

        role.removeResponsibleGroup(this);
        return true;
    }

    @Override
    public abstract Set<? extends IUserPrincipal> getMemberUsers();

    @SuppressWarnings("unchecked")
    @Override
    public boolean addMemberUser(IUserPrincipal user) {
        if (user == null)
            throw new NullPointerException("user");

        Set<IUserPrincipal> memberUsers = (Set<IUserPrincipal>) getMemberUsers();
        if (!memberUsers.add(user))
            return false;

        user.addAssignedGroup(this);
        return true;
    }

    @Override
    public boolean removeMemberUser(IUserPrincipal user) {
        if (user == null)
            throw new NullPointerException("user");

        if (!getMemberUsers().remove(user))
            return false;

        user.removeAssignedGroup(this);
        return true;
    }

    @Override
    public AbstractGroup detach() {
        super.detach();

        for (IRolePrincipal role : flyOver(getAssignedRoles()))
            role.removeResponsibleGroup(this);

        for (IUserPrincipal user : flyOver(getMemberUsers()))
            user.removeAssignedGroup(this);

        return this;
    }

    @Override
    public boolean implies(IPrincipal principal) {
        if (super.implies(principal))
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
