package com.bee32.icsf.access.authority;

import com.bee32.icsf.access.acl.ACL;

public class RootAuthority
        extends Authority {

    RootAuthority() {
        super("Root", new ACL());
    }

    private static RootAuthority instance = new RootAuthority();

    public static RootAuthority getInstance() {
        return instance;
    }

}
