package com.bee32.icsf.access.acl;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.Principal;

public interface IACL {

    IACL EMPTY = EmptyACL.getInstance();

    /**
     * 获取继承的 ACL。
     *
     * @return <code>null</code> 如果本 ACL 没有继承项。
     */
    IACL getInheritedACL();

    /**
     * 平面化，合并本 ACL 与继承的 ACL。
     *
     * 平面化可提高 ACL 的搜索效率。
     *
     * @return Non-<code>null</code> flatten ACL.
     */
    IACL flatten();

    /**
     * 获取本 ACL （局部）定义的权限条目数。
     */
    int size();

    /**
     * 列出本 ACL （局部）定义的所有权限条目。
     *
     * @return 非 <code>null</code> 的 {@link Entry} 集合。。
     */
    Map<? extends Principal, Permission> getDeclaredEntries();

    /**
     * 本 ACL 的有效主体集（局部）。
     *
     * 这个集合并不表明所含的主体具有对应权限，而仅仅表明本 ACL 对列出的主体集有影响力。
     *
     * @return 非 <code>null</code> {@link Principal} 集合。
     */
    Set<? extends Principal> getDeclaredPrincipals();

    /**
     * Test if given principal is declared in this ACL.
     *
     * @param principal
     *            Non-<code>null</code> principal.
     * @return <code>null</code> If principal isn't declared in the local acl.
     */
    Permission getDeclaredPermission(Principal principal);

    /**
     * 本 ACL 的有效主体集（包括继承项）。
     *
     * 这个集合并不表明所含的主体具有对应权限，而仅仅表明本 ACL 对列出的主体集有影响力。
     *
     * @return 非 <code>null</code> {@link Principal} 集合。
     */
    Set<? extends Principal> getEffectivePrincipals();

    /**
     * Get the effective permission.
     *
     * @param principal
     *            The principal whose permission is to be queried.
     * @return <code>null</code> if the specified <code>principal</code> isn't listed in this ACL,
     *         nor in the inherited ACLs.
     */
    Permission getEffectivePermission(Principal principal);

    /**
     * Find principals with the required permission.
     *
     * @param requiredPermission
     *            The required permission.
     * @return Non-<code>null</code> principal collection who has the given permission to the
     *         resource described by this ACL.
     */
    Collection<? extends Principal> findPrincipals(Permission requiredPermission);

    /**
     * Find principals with the required permission.
     *
     * @param requiredMode
     *            The permission mode required.
     * @return Non-<code>null</code> principal collection who has the given permission to the
     *         resource described by this ACL.
     */
    Collection<? extends Principal> findPrincipals(String requiredMode);

    /**
     * 添加一个局部权限条目。
     *
     * @throws UnsupportedOperationException
     *             如果本 ACL 是只读的。
     */
    void add(Entry<? extends Principal, Permission> entry);

    /**
     * 添加一个局部权限条目。
     *
     * 如果在局部 ACL 中已经定义了 principal 的权限，那么这两个权限条目将合并为一个。
     *
     * @throws UnsupportedOperationException
     *             如果本 ACL 是只读的。
     */
    Permission add(Principal principal, Permission permission);

    /**
     * 添加一个局部权限条目。
     *
     * 如果在局部 ACL 中已经定义了 principal 的权限，那么这两个权限条目将合并为一个。
     *
     * @throws UnsupportedOperationException
     *             如果本 ACL 是只读的。
     */
    Permission add(Principal principal, String mode);

    /**
     * 删除一个局部权限条目。
     *
     * @throws NullPointerException
     *             如果 <code>entry</code> 为 <code>null</code>.
     * @throws UnsupportedOperationException
     *             如果本 ACL 是只读的。
     */
    boolean remove(Entry<? extends Principal, Permission> entry);

    /**
     * 从局部 ACL 中删除含有指定 principal 的权限条目。
     *
     * @return <code>true</code> 存在 principal 的条目并且成功删除，否则返回 <code>false</code>。
     * @throws NullPointerException
     *             如果 <code>principal</code> 为 <code>null</code>.
     */
    boolean remove(Principal principal);

}
