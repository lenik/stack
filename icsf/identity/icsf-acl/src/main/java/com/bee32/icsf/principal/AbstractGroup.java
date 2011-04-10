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
    public void addAssignedRole(IRolePrincipal role) {
        Set<IRolePrincipal> assignedRoles = (Set<IRolePrincipal>) getAssignedRoles();
        assignedRoles.add(role);
    }

    @Override
    public void removeAssignedRole(IRolePrincipal role) {
        getAssignedRoles().remove(role);
    }

    @Override
    public abstract Set<? extends IUserPrincipal> getMemberUsers();

    @SuppressWarnings("unchecked")
    @Override
    public void addMemberUser(IUserPrincipal user) {
        Set<IUserPrincipal> memberUsers = (Set<IUserPrincipal>) getMemberUsers();
        memberUsers.add(user);
    }

    @Override
    public void removeMemberUser(IUserPrincipal user) {
        getMemberUsers().remove(user);
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
