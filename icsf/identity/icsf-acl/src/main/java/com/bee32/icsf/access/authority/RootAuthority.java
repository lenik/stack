package com.bee32.icsf.access.authority;

import com.bee32.icsf.access.alt.ACLDao;

public class RootAuthority
        extends Authority {

    RootAuthority() {
        super("Root");
    }

    @Override
    public boolean trusts(IAuthority authority) {
        if (authority == null)
            throw new NullPointerException("authority");

        if (authority instanceof ACLDao)
            return true;

        return false;
    }

    private static final RootAuthority instance = new RootAuthority();

    public static RootAuthority getInstance() {
        return instance;
    }

}
