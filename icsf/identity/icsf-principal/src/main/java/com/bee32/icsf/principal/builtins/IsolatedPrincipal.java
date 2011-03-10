package com.bee32.icsf.principal.builtins;

import java.io.Serializable;

import com.bee32.icsf.principal.AbstractPrincipal;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.IPrincipalVisitor;

public class IsolatedPrincipal
        extends AbstractPrincipal
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public IsolatedPrincipal(String name) {
        if (name == null)
            throw new NullPointerException("name");
    }

    @Override
    public boolean implies(IPrincipal principal) {
        return ((Object) this).equals(principal);
    }

    @Override
    public void accept(IPrincipalVisitor visitor) {
        if (visitor.startPrincipal(this))
            visitor.endPrincipal(this);
    }

    @Override
    public String toString() {
        return "Isolated :: " + name;
    }

}
