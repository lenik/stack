package com.tinylily.model.base.schema.impl;

import net.bodz.bas.repr.path.PathToken;

import com.bee32.zebra.tk.sql.VhostDataService;
import com.tinylily.repr.CoEntityManager;

/**
 * 对象属性的形而上定义。
 * 
 * 一个对象可以有多个属性，但同名的属性只能出现一次。
 * 
 * @label 属性定义
 */
@PathToken("att")
public class AttributeDefManager
        extends CoEntityManager {

    AttributeDefMapper mapper;

    public AttributeDefManager() {
        mapper = VhostDataService.getInstance().getMapper(AttributeDefMapper.class);
    }

    public AttributeDefMapper getMapper() {
        return mapper;
    }

}
