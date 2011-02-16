package com.bee32.sems.security.access;

public abstract class AbstractRole
        extends AbstractPrincipal
        implements IRolePrincipal {

    private static final long serialVersionUID = 1L;

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
