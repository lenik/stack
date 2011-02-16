package com.bee32.sems.security.access.acl;

import java.util.Collection;

import com.bee32.sems.security.access.IPrincipal;
import com.bee32.sems.security.access.Permission;
import com.bee32.sems.security.access.PermissionException;

public interface IACL {

    IACL EMPTY = EmptyACL.getInstance();

    /**
     * 获取继承的 ACL。
     *
     * @return <code>null</code> 如果本 ACL 没有继承项。
     */
    IACL getInheritedACL();

    /**
     * 本 ACL 的主体唯一集。
     *
     * @return 非 <code>null</code> {@link IPrincipal} 集合。
     */
    Collection<? extends IPrincipal> getDeclaredPrincipals();

    /**
     * 选取指定主体可用的权限集。
     *
     * @return 非 <code>null</code> 值。
     */
    IACL select(IPrincipal selectPrincipal);

    /**
     * 选取蕴涵指定权限的主体集。
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
     * {@link #getACLPolicy()}.checkPermission(this, requiredPermission);
     * </pre>
     *
     * @see {@link IACLPolicy#checkPermission(IACL, Permission)}
     */
    void checkPermission(Permission requiredPermission)
            throws PermissionException;

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
     * 列出本 ACL （局部）定义的所有权限条目。
     *
     * @return 非 <code>null</code> 的 {@link Entry} 集合。。
     */
    Collection<? extends Entry> getEntries();

    /**
     * 添加一个权限条目。
     *
     * @throws UnsupportedOperationException
     *             如果本 ACL 是只读的。
     */
    void add(Entry entry);

    /**
     * 删除一个权限条目。
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
