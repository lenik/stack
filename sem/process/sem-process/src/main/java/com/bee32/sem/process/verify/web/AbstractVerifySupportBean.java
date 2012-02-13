package com.bee32.sem.process.verify.web;

import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.process.verify.service.IVerifyService;

public abstract class AbstractVerifySupportBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    protected IVerifyService getVerifyService() {
        return ctx.bean.getBean(IVerifyService.class);
    }

}
