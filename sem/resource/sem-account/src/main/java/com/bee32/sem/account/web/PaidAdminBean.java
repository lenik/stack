package com.bee32.sem.account.web;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.sem.account.dto.PaidDto;
import com.bee32.sem.account.entity.Paid;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.service.PeopleService;

public class PaidAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public PaidAdminBean() {
        super(Paid.class, PaidDto.class, 0, new Equals("class", "PED"));
    }

    /**
     * 在页面上使用，使用户选择部门时只出现本公司的部门
     *
     * @return
     */
    public Integer getSelfOrgId() {
        return ctx.bean.getBean(PeopleService.class).getSelfOrg().getId();
    }
}
