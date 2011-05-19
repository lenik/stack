package com.bee32.icsf.principal;

import java.security.Principal;
import java.util.Collection;

import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.orm.entity.IEntity;

/**
 * 安全主体。
 *
 * 在涉及安全的操作中，代表行为主体的身份抽象。
 */
public interface IPrincipal
        extends Principal, IEntity<Integer> {

    /**
     * 主体在该主体所属域中的唯一名称。
     *
     * @return non-<code>null</code> value.
     */
    String getName();

    /**
     * 主体的显示名称。
     *
     * 如用户姓名、企业名称、角色名称等。
     */
    String getDisplayName();

    /**
     * 描述信息，如用户全称、企业全称、角色的描述信息等。
     *
     * @return <code>null</code> 如果描述信息不可用。
     */
    String getDescription();

    /**
     * 主体的肖像。
     *
     * @see IAppearance#getImageMap()
     */
    String IMAGE_PORTRAIT = "portrait";

    /**
     * 判断蕴含关系。
     *
     * @param principal
     *            待测试的被蕴含的安全主体。
     * @return 仅当本主体蕴含了指定的 <code>principal</code> 则返回 <code>true</code>，如果蕴含关系不能确定或表示不蕴含则返回
     *         <code>false</code>。
     * @see PrincipalImplication#getLUB()
     */
    boolean implies(IPrincipal principal);

    boolean impliesOneOf(Collection<? extends IPrincipal> principals);

    boolean impliesAllOf(Collection<? extends IPrincipal> principals);

    /**
     * 判断被任一蕴含关系。
     *
     * @param principals
     *            待测试的蕴含的安全主体。
     * @return 当任一指定的 <code>principals</code> 蕴含了本主体则返回 <code>true</code>，否则返回 <code>false</code>。
     */
    boolean impliedByOneOf(Collection<? extends IPrincipal> principals);

    /**
     * 判断被全部蕴含关系。
     *
     * @param principals
     *            待测试的蕴含的安全主体。
     * @return 当所有指定的 <code>principals</code> 蕴含了本主体则返回 <code>true</code>，否则返回 <code>false</code>。
     */
    boolean impliedByAllOf(Collection<? extends IPrincipal> principals);

    void accept(IPrincipalVisitor visitor);

}
