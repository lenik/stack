package com.bee32.icsf.access.acl;

import com.bee32.icsf.access.Permission;

public interface IACLPolicy {

    /**
     * Check if the required permission is allowed by this <code>acl</code>.
     *
     * @param acl
     *            The acl provided.
     * @param permission
     *            The required permission.
     * @return <code>true</code> If the specified <code>permission</code> is allowed by this
     *         <code>acl</code>. Otherwise <code>false</code> is returned.
     */
    boolean isAllowed(IACL acl, Permission permission);

    /**
     * Check if the required permission is denied by this <code>acl</code>.
     *
     * @param acl
     *            The acl provided.
     * @param permission
     *            The required permission.
     * @return <code>true</code> If the specified <code>permission</code> is denied by this
     *         <code>acl</code>. Otherwise <code>false</code> is returned.
     */
    boolean isDenied(IACL acl, Permission permission);

}
