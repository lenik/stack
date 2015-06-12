package com.bee32.zebra.io.art.impl;

import net.bodz.bas.err.ParseException;
import net.bodz.lily.model.base.CoNodeCriteria;
import net.bodz.lily.model.sea.QVariantMap;

/**
 * @see com.bee32.zebra.io.art.ArtifactCategory
 */
public class ArtifactCategoryCriteria
        extends CoNodeCriteria {

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
    }

    public static ArtifactCategoryCriteria below(int maxDepth) {
        ArtifactCategoryCriteria criteria = new ArtifactCategoryCriteria();
        criteria.maxDepth = 1;
        return criteria;
    }

}
