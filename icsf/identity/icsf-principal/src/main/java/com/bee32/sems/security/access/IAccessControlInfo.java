package com.bee32.sems.security.access;

import java.util.Collection;

/**
 * 访问受控对象的元数据。访问者必须被授予必要的安全权限以访问本对象及对象内的属性、和调用对象中的操作。
 *
 * 受控对象可能是：
 * <ul>
 * <li>关键资源，如机密文件、隐私数据等。
 * <li>关键过程，如系统管理、数据库操作、重要的业务过程等。
 * <li>领域边界，用于定义如公司部门边界，网络域等。
 * </ul>
 */
public interface IAccessControlInfo {

    /**
     * 列出所有需要授权的操作。（类方法）
     *
     * 列出可见的操作集供管理员查看、授权用。
     *
     * （也可能有未列出的不可见的受控操作，比如与系统底层的交互， 不希望由用户来配置授权，而仅根据系统默认的权限决定的。）
     *
     * @return 返回不是 <code>null</code> 的 {@link IGuardedOperation} 集合。
     */
    Collection<? extends IManagedOperation> getManagedOperations();

    /**
     * 根据操作名称返回所需权限， 等效于：
     *
     * <pre>
     * {
     *     for (IManagedOperation o : getManagedOperations()) {
     *         if (operationName.equals(o.getName()))
     *             return o.getRequiredPermission();
     *     }
     *     return null;
     * }
     * </pre>
     */
    Permission getRequiredPermission(String operationName);

}
