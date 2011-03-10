package com.bee32.icsf.principal.dao;

import javax.inject.Inject;

import org.springframework.context.ApplicationContext;

import com.bee32.icsf.principal.SampleStore;
import com.bee32.icsf.principal.UserBean;
import com.bee32.plover.orm.entity.AbstractDao;

public class UserDao
        extends AbstractDao<UserBean, Long> {

    public UserDao() {
        super(UserBean.class, Long.class);
    }

    {
        // applicationContext.
        addNormalSample(//
                SampleStore.eva, //
                SampleStore.wallE, //
                SampleStore.tom);
        // addNormalSample(SampleStore.solaGroup, SampleStore);
    }

    @Inject
    static ApplicationContext applicationContext;

    @Inject
    static ApplicationContext debugContext;;
}
