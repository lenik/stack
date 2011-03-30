package com.bee32.icsf.access.alt;

import com.bee32.icsf.access.builtins.GeneralPermission;
import com.bee32.icsf.principal.SampleStore;
import com.bee32.plover.orm.entity.AbstractDao;

public class R_ACEDao
        extends AbstractDao<R_ACE, Long> {

    public R_ACEDao() {
        super(R_ACE.class, Long.class);
    }

    {
        SampleStore store = SampleStore.getInstance();

        R_ACE kate_READ = new R_ACE(store.kate, GeneralPermission.READ, true);
        addNormalSample(kate_READ);
    }

}
