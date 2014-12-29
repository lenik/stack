package com.bee32.zebra.io.art.impl;

import net.bodz.bas.repr.path.PathToken;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.io.art.ArtifactCategory;
import com.tinylily.repr.CoEntityManager;

/**
 * 物料分类
 * 
 * @label 物料分类
 * 
 * @rel art/: 管理物料
 */
@PathToken("acat")
public class ArtifactCategoryManager
        extends CoEntityManager {

    public ArtifactCategoryManager(IQueryable context) {
        super(ArtifactCategory.class, context);
    }

}
