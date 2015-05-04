package com.bee32.zebra.sys.security.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;
import net.bodz.lily.model.base.security.Group;

import com.bee32.zebra.tk.repr.QuickIndex;

/**
 * 用户组
 * <p>
 * 用于对用户帐户进行分类和权限控制。
 * 
 * 一个用户可以从属于多个用户组，一个用户组下面也可以有多个用户。
 * 
 * @label 用户组
 * 
 * @rel user/: 管理用户
 * @rel org/: 管理企、事业组织
 */
@ObjectType(Group.class)
public class GroupIndex
        extends QuickIndex {

    public GroupIndex(IQueryable context) {
        super(context);
    }

}
