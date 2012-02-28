package com.bee32.plover.orm.util;

import com.bee32.plover.arch.util.BeanPartialContext;
import com.bee32.plover.orm.dao.CommonDataManager;

public class WiredDataPartialContext
        extends DataPartialContext {

    final BeanPartialContext bean;

    public WiredDataPartialContext(BeanPartialContext bean) {
        if (bean == null)
            throw new NullPointerException("bean");
        this.bean = bean;
    }

    public CommonDataManager getDataManager() {
        return bean.getBean(CommonDataManager.class);
    }

}
