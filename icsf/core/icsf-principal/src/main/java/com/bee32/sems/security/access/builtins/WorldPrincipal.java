package com.bee32.sems.security.access.builtins;

import java.io.Serializable;

import com.bee32.sems.security.access.AbstractPrincipal;
import com.bee32.sems.security.access.IPrincipal;
import com.bee32.sems.security.access.IPrincipalVisitor;

public final class WorldPrincipal
        extends AbstractPrincipal
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
