package com.bee32.icsf.principal.dao;

import com.bee32.icsf.principal.SampleStore;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.AbstractDao;

public class UserDao
        extends AbstractDao<User, Long> {

    public UserDao() {
        super(User.class, Long.class);
    }

    {
        SampleStore sampleStore = SampleStore.getInstance();

        addNormalSample(//
                sampleStore.eva, //
                sampleStore.wallE, //
                sampleStore.alice, //
                sampleStore.tom, //
                sampleStore.kate //
        );
    }

}
