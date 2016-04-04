package com.bee32.zebra.io.art.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.lily.model.base.CoNodeMask;
import net.bodz.lily.model.sea.QVariantMap;

/**
 * @see com.bee32.zebra.io.art.ArtifactCategory
 */
public class ArtifactCategoryMask
        extends CoNodeMask {

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
    }

    public static ArtifactCategoryMask below(int maxDepth) {
        ArtifactCategoryMask mask = new ArtifactCategoryMask();
        mask.maxDepth = 1;
        return mask;
    }

}
