package com.bee32.plover.orm.util;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.test.FeaturePlayer;

@Import(WiredDaoTestCase.class)
@Transactional(readOnly = true)
public abstract class WiredDaoFeat<T extends WiredDaoFeat<T>>
        extends FeaturePlayer<T> {

    @Inject
    protected CommonDataManager dataManager;

}
