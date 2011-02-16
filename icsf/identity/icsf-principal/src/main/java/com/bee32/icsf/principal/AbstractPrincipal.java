package com.bee32.icsf.principal;

import com.bee32.plover.arch.Component;

public abstract class AbstractPrincipal
        extends Component
        implements IPrincipal {

    private static final long serialVersionUID = 1L;

    public AbstractPrincipal() {
        super();
    }

    public AbstractPrincipal(String name) {
        super(name);
    }

    @Override
    public String toString() {
        String principalType = getClass().getSimpleName();
        return principalType + " :: " + getName();
    }

}
