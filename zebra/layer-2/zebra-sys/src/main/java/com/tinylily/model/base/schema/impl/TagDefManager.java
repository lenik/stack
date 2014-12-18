package com.tinylily.model.base.schema.impl;

import net.bodz.bas.repr.path.PathToken;

import com.bee32.zebra.tk.sea.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;
import com.tinylily.model.base.schema.TagDef;

/**
 * 标签用于对事物进行分类。
 * 
 * @label 标签定义
 */
@PathToken("tag")
public class TagDefManager
        extends FooManager {

    TagDefMapper mapper;

    public TagDefManager() {
        super(TagDef.class);
        mapper = VhostDataService.getInstance().getMapper(TagDefMapper.class);
    }

    public TagDefMapper getMapper() {
        return mapper;
    }

}
