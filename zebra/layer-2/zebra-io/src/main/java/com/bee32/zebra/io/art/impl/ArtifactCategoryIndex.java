package com.bee32.zebra.io.art.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.io.art.ArtifactCategory;
import com.bee32.zebra.tk.sea.FooIndex;

/**
 * 物料分类
 * 
 * @label 物料分类
 * 
 * @rel art/: 管理物料
 */
@ObjectType(ArtifactCategory.class)
public class ArtifactCategoryIndex
        extends FooIndex {

    public ArtifactCategoryIndex(IQueryable context) {
        super(context);
    }

}