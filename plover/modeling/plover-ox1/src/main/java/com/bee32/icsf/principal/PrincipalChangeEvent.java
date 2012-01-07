package com.bee32.icsf.principal;

import java.util.EventObject;

public class PrincipalChangeEvent
        extends EventObject {

    private static final long serialVersionUID = 1L;

    final Principal principal;
    final int flags;

    public PrincipalChangeEvent(Principal principal, int flags) {
        super(principal);
        this.principal = principal;
        this.flags = flags;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public int getFlags() {
        return flags;
    }

}
