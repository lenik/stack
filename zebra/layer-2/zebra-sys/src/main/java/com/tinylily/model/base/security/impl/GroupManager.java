package com.tinylily.model.base.security.impl;

import com.bee32.zebra.tk.sql.VhostDataService;
import com.tinylily.model.base.security.Group;
import com.tinylily.repr.CoEntityManager;

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
public class GroupManager
        extends CoEntityManager {

    GroupMapper mapper;

    public GroupManager() {
        super(Group.class);
        mapper = VhostDataService.getInstance().getMapper(GroupMapper.class);
    }

    public GroupMapper getMapper() {
        return mapper;
    }

}
