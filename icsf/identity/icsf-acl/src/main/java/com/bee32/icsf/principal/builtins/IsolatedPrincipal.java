package com.bee32.icsf.principal.builtins;

import java.io.Serializable;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.IPrincipalVisitor;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.PrettyPrintStream;

public class IsolatedPrincipal
        extends Principal
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
    public void toString(PrettyPrintStream out, FormatStyle format) {
        out.print("Isolated :: " + name);
    }

}
