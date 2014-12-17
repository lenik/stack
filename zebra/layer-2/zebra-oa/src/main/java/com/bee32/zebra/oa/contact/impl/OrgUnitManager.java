package com.bee32.zebra.oa.contact.impl;

import com.bee32.zebra.tk.sql.VhostDataService;
import com.tinylily.repr.CoEntityManager;

/**
 * 企、事业组织下属的单位、科室、部门。
 * 
 * @rel org/: 管理企、事业组织
 * @rel person/: 管理联系人
 * @rel tag/?schema=10: 管理标签
 * 
 * @label 组织机构/单位/部门
 */
public class OrgUnitManager
        extends CoEntityManager {

    OrgUnitMapper mapper;

    public OrgUnitManager() {
        mapper = VhostDataService.getInstance().getMapper(OrgUnitMapper.class);
    }

    public OrgUnitMapper getMapper() {
        return mapper;
    }

}
