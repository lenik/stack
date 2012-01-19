package com.bee32.plover.orm.util;

import java.io.Serializable;

import javax.inject.Inject;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.site.scope.SiteNaming;
import com.bee32.plover.test.FeaturePlayer;

@Import(WiredDaoTestCase.class)
// @Transactional(readOnly = true)
public abstract class WiredDaoFeat<T extends WiredDaoFeat<T>>
        extends FeaturePlayer<T> {

    @Inject
    protected CommonDataManager dataManager;

    public WiredDaoFeat() {
        SiteNaming.setDefaultSiteName("feat");
    }

    protected <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> asFor(Class<E> entityType) {
        IEntityAccessService<E, K> service = dataManager.asFor(entityType);
        return service;
    }

}
