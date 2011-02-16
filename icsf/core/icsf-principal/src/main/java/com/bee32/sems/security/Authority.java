package com.bee32.sems.security;

import java.util.HashSet;
import java.util.Set;

import com.bee32.sems.security.access.acl.IACL;

public class Authority
        implements IAuthority {

    private final String name;
    private IACL acl;
    private Set<IAuthority> trustedAuthorities;

    public Authority(String name, IACL acl) {
        if (name == null)
            throw new NullPointerException("name");
        if (acl == null)
            throw new NullPointerException("acl");
        this.name = name;
        this.acl = acl;
        this.trustedAuthorities = new HashSet<IAuthority>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IACL getACL() {
        return acl;
    }

    @Override
    public boolean isTrusted(IAuthority authority) {
        return trustedAuthorities.contains(authority);
    }

}
