package com.bee32.zebra.sys.security.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.lily.model.base.security.User;

import com.bee32.zebra.tk.repr.QuickIndex;

/**
 * 使用本系统的用户帐户。
 * <p>
 * 帐户可以用来登录本系统。 一个联系人可以有多个帐户。联系人可以使客户、供应商，而帐户不区分这些属性。 帐户也没有可以用于分类的标签。
 * 系统中的“操作员”，“经办人”等通常是本企业内的用户，默认为当前登录并进行操作的用户。 如果要输入其它联系人作为“操作员”、“经办人”等，需要建立相应的帐户，而不可以直接用联系人来代替。
 * 
 * @label 用户帐户
 * 
 * @rel group/: 管理用户组
 * @rel person/: 管理联系人
 */
@ObjectType(User.class)
public class UserIndex
        extends QuickIndex {

}
