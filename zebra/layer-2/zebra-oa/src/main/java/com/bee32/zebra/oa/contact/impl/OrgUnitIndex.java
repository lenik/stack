package com.bee32.zebra.oa.contact.impl;

import net.bodz.bas.meta.decl.ObjectType;

import com.bee32.zebra.oa.contact.OrgUnit;
import com.bee32.zebra.tk.repr.QuickIndex;

/**
 * 企、事业组织下属的单位、科室、部门。
 * 
 * @rel org/: 管理企、事业组织
 * @rel person/: 管理联系人
 * @rel tag/?schema=10: 管理标签
 * 
 * @label 组织机构/单位/部门
 */
@ObjectType(OrgUnit.class)
public class OrgUnitIndex
        extends QuickIndex<OrgUnit> {

}
