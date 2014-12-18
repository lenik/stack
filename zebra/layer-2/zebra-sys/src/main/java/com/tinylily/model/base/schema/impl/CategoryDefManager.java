package com.tinylily.model.base.schema.impl;

import net.bodz.bas.repr.path.PathToken;

import com.bee32.zebra.tk.sea.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;
import com.tinylily.model.base.schema.CategoryDef;

/**
 * 用于对象分类。
 * <p>
 * 和标签不同，一个对象只能属于一个类别。
 * 
 * @label 类别
 */
@PathToken("cat")
public class CategoryDefManager
        extends FooManager {

    CategoryDefMapper mapper;

    public CategoryDefManager() {
        super(CategoryDef.class);
        mapper = VhostDataService.getInstance().getMapper(CategoryDefMapper.class);
    }

    public CategoryDefMapper getMapper() {
        return mapper;
    }

}
