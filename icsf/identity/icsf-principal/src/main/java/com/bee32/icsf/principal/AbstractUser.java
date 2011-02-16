package com.bee32.icsf.principal;

public abstract class AbstractUser
        extends AbstractPrincipal
        implements IUserPrincipal {

    private static final long serialVersionUID = 1L;

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
