package com.bee32.icsf.access.authority;

import com.bee32.icsf.access.acl.IACL;

public interface IAuthority {

    String getName();

    /**
     * 返回访问控制列表（ACL）。
     *
     * @see {@link IACL#getInheritedACL()}
     *
     * @return 非 <code>null</code> 值。
     */
    IACL getACL();

    boolean isTrusted(IAuthority authority);

    IAuthority ROOT = RootAuthority.getInstance();

}
