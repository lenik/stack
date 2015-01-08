package com.tinylily.model.base.security.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.tk.sea.FooIndex;
import com.tinylily.model.base.security.Group;

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
        extends FooIndex {

    public GroupIndex(IQueryable context) {
        super(context);
    }

}
