package com.bee32.icsf.access.authority;

import java.util.HashSet;
import java.util.Set;

import com.bee32.plover.arch.Component;

public abstract class Authority
        extends Component
        implements IAuthority {

    private Set<IAuthority> trustedAuthorities = new HashSet<IAuthority>();

    public Authority() {
        super();
    }

    public Authority(String name) {
        super(name);
    }

    @Override
    public boolean trusts(IAuthority authority) {
        return trustedAuthorities.contains(authority);
    }

}
