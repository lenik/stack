package com.bee32.icsf.principal.builtins;

import java.io.Serializable;

import com.bee32.icsf.principal.AbstractPrincipal;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.IPrincipalVisitor;

public class IsolatedPrincipal
        extends AbstractPrincipal
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;

    public IsolatedPrincipal(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean implies(IPrincipal principal) {
        return equals(principal);
    }

    @Override
    public void accept(IPrincipalVisitor visitor) {
        if (visitor.startPrincipal(this))
            visitor.endPrincipal(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IsolatedPrincipal)
            return name.equals(((IsolatedPrincipal) obj).name);
        return false;
    }

    @Override
    public int hashCode() {
        return 0xa86bdda9 + name.hashCode();
    }

    @Override
    public String toString() {
        return "Isolated :: " + name;
    }

}
