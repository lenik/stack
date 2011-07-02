package com.bee32.icsf.principal.builtins;

import java.io.Serializable;

import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.IPrincipalVisitor;
import com.bee32.icsf.principal.Principal;

public final class WorldPrincipal
        extends Principal<WorldPrincipal>
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String getName() {
        return "World";
    }

    @Override
    public boolean implies(IPrincipal principal) {
        return true;
    }

    @Override
    public void accept(IPrincipalVisitor visitor) {
        if (visitor.startPrincipal(this))
            visitor.endPrincipal(this);
    }

    static final WorldPrincipal instance = new WorldPrincipal();

    public static WorldPrincipal getInstance() {
        return instance;
    }

}
