package com.bee32.sem.account.web;

import com.bee32.sem.account.dto.ReceivableInitDto;
import com.bee32.sem.account.entity.ReceivableInit;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.service.PeopleService;

public class ReceivableInitAdminBean extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public ReceivableInitAdminBean() {
        super(ReceivableInit.class, ReceivableInitDto.class, 0);
    }

    /**
     * 在页面上使用，使用户选择部门时只出现本公司的部门
     * @return
     */
    public Integer getSelfOrgId() {
        return ctx.bean.getBean(PeopleService.class).getSelfOrg().getId();
    }

}
