package com.bee32.icsf.principal.dao;

import com.bee32.icsf.principal.GroupBean;
import com.bee32.icsf.principal.SampleStore;
import com.bee32.plover.orm.entity.AbstractDao;

public class GroupDao
        extends AbstractDao<GroupBean, Long> {

    public GroupDao() {
        super(GroupBean.class, Long.class);
    }

    {
        SampleStore sampleStore = SampleStore.getInstance();

        addNormalSample(//
                sampleStore.solaGroup, //
                sampleStore.sunCorp //
        );
    }

}
