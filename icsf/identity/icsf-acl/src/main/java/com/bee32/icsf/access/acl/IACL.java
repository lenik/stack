package com.bee32.icsf.access.acl;

import java.util.Collection;
import java.util.Set;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.IPrincipal;

public interface IACL {

    IACL EMPTY = EmptyACL.getInstance();

    /**
     * 获取继承的 ACL。
     *
     * @return <code>null</code> 如果本 ACL 没有继承项。
     */
    IACL getInheritedACL();

    /**
     * 本 ACL 的有效主体集（局部）。
     *
     * 这个集合并不表明所含的主体具有对应权限，而仅仅表明本 ACL 对列出的主体集有影响力。
     *
     * @return 非 <code>null</code> {@link IPrincipal} 集合。
     */
    Set<? extends IPrincipal> getDeclaredRelatedPrincipals();

    /**
     * 本 ACL 的有效主体集（包括继承项）。
     *
     * 这个集合并不表明所含的主体具有对应权限，而仅仅表明本 ACL 对列出的主体集有影响力。
     *
     * @return 非 <code>null</code> {@link IPrincipal} 集合。
     */
    Set<? extends IPrincipal> getRelatedPrincipals();

    /**
     * Test if given principal is declared in this ACL.
     *
     * @param principal
     *            Non-<code>null</code> principal.
     * @return <code>true</code> If given principal is declared in this ACL.
     */
    boolean isDeclaredRelated(IPrincipal principal);

    /**
     * Test if given principal in declared in this ACL or in the inherited ACLs.
     *
     * @param principal
     *            Non-<code>null</code> principal.
     * @return <code>true</code> If given principal is declared in this ACL or one of the inherited
     *         ACLs.
     */
    boolean isRelated(IPrincipal principal);

    /**
     * 选取指定主体相关的 ACL 子集。
     *
     * @return 非 <code>null</code> 值。
     */
    IACL select(IPrincipal selectPrincipal);

    /**
     * 选取蕴涵指定权限的 ACL 子集。
     *
     * @return 非 <code>null</code> 值。
     */
    IACL select(Permission selectPermission);

    /**
     * 选取指定主体的蕴涵的指定权限的子集。
     *
     * @return 非 <code>null</code> 值。
     */
    IACL select(IPrincipal selectPrincipal, Permission selectPermission);

    /**
     * 获取 ACL 解释策略。
     *
     * @return 非 <code>null</code> {@link IACLPolicy}。
     */
    IACLPolicy getACLPolicy();

    /**
     * 等价于
     *
     * <pre>
     * {@link #getACLPolicy()}.isAllowed(this, permission);
     * </pre>
     *
     * @see {@link IACLPolicy#isAllowed(IACL, Permission)}
     */
    boolean isAllowed(Permission permission);

    /**
     * 等价于
     *
     * <pre>
     * {@link #getACLPolicy()}.isDenied(this, permission);
     * </pre>
     *
     * @see {@link IACLPolicy#isDenied(IACL, Permission)}
     */
    boolean isDenied(Permission permission);

    /**
     * ACL 的条目（ACE）。
     */
    interface Entry {

        IPrincipal getPrincipal();

        Permission getPermission();

        boolean isAllowed();

        boolean isDenied();

    }

    /**
     * 获取本 ACL （局部）定义的权限条目数。
     */
    int size();

    /**
     * 列出本 ACL （局部）定义的所有权限条目。
     *
     * @return 非 <code>null</code> 的 {@link Entry} 集合。。
     */
    Collection<? extends Entry> getEntries();

    /**
     * 添加一个局部权限条目。
     *
     * @throws UnsupportedOperationException
     *             如果本 ACL 是只读的。
     */
    void add(Entry entry);

    /**
     * 删除一个局部权限条目。
     *
     * @throws UnsupportedOperationException
     *             如果本 ACL 是只读的。
     */
    boolean remove(Entry entry);

    /**
     * 等价于：
     *
     * <pre>
     * int count = 0;
     * for (Entry entry : this.select(principal).getEntries())
     *     if (acl.remove(entry))
     *         count++;
     * return count;
     * </pre>
     *
     * @return 删除的记录数。
     */
    int remove(IPrincipal principal);

    /**
     * 等价于：
     *
     * <pre>
     * int count = 0;
     * for (Entry entry : this.select(permission).getEntries())
     *     if (acl.remove(entry))
     *         count++;
     * return count;
     * </pre>
     *
     * @return 删除的记录数。
     */
    int remove(Permission permission);

    /**
     * 等价于：
     *
     * <pre>
     * int count = 0;
     * for (Entry entry : this.select(principal, permission).getEntries())
     *     if (acl.remove(entry))
     *         count++;
     * return count;
     * </pre>
     *
     * @return 删除的记录数。
     */
    int remove(IPrincipal principal, Permission permission);

}
