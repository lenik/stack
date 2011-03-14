package com.bee32.icsf.principal;

import java.util.Set;

public abstract class AbstractRole
        extends AbstractPrincipal
        implements IRolePrincipal {

    private static final long serialVersionUID = 1L;

    public AbstractRole() {
        super();
    }

    public AbstractRole(String name) {
        super(name);
    }

    @Override
    public abstract Set<IUserPrincipal> getResponsibleUsers();

    @Override
    public void addResponsibleUser(IUserPrincipal user) {
        getResponsibleUsers().add(user);
    }

    @Override
    public void removeResponsibleUser(IUserPrincipal user) {
        getResponsibleUsers().remove(user);
    }

    @Override
    public abstract Set<IGroupPrincipal> getResponsibleGroups();

    @Override
    public void addResponsibleGroup(IGroupPrincipal group) {
        getResponsibleGroups().add(group);
    }

    @Override
    public void removeResponsibleGroup(IGroupPrincipal group) {
        getResponsibleGroups().remove(group);
    }

    @Override
    public boolean implies(IPrincipal principal) {
        if (principal == null)
            throw new NullPointerException("principal");

        if (this.equals(principal))
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
