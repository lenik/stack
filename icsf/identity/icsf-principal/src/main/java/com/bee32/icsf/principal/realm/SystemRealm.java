package com.bee32.icsf.principal.realm;

import com.bee32.icsf.principal.IPrincipal;

public class SystemRealm
        extends AbstractRealm {

    private static final long serialVersionUID = 1L;

    public SystemRealm() {
        super();
    }

    public SystemRealm(String name) {
        super(name);
    }

    @Override
    public Integer getPrimaryKey() {
        return -1;
    }

    @Override
    public boolean contains(IPrincipal principal) {
        return true;
    }

}
