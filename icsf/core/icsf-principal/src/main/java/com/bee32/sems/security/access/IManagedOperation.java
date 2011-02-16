package com.bee32.sems.security.access;

/**
 * 访问受控操作
 */
public interface IManagedOperation {

    /**
     * 操作的名称，必须和 {@link IAccessControlInfo#getRequiredPermission(String)} 中的名称一致。
     */
    String getName();

    /**
     * 操作的显示名称。
     *
     * @return non-<code>null</code> string.
     */
    String getDisplayName();

    /**
     * 操作的描述信息。
     *
     * @return non-<code>null</code> string.
     */
    String getDescription();

    /**
     * 公开操作，在访问控制管理中应该让用户配置的项目。
     */
    int PUBLIC = 0;

    /**
     * 私操作，在访问控制管理中对用户隐藏的项目。
     */
    int PRIVATE = 1;

    /**
     * 模块的内部操作，在访问控制管理中仅对有经验的高级用户可见的项目。
     */
    int MODULE = 2;

    /**
     * 操作的可见性。
     *
     * @see #PUBLIC
     * @see #MODULE
     * @see #PRIVATE
     */
    int getVisibility();

    /**
     * 访问本操作所需的最低权限（或推荐权限）。
     *
     * @return <code>null</code> if no premission required.
     */
    Permission getRequiredPermission();

}
