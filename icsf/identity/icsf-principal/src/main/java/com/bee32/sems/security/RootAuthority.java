package com.bee32.sems.security;

import com.bee32.sems.security.access.acl.ACL;

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
