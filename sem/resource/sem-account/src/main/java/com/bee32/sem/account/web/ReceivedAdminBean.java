package com.bee32.sem.account.web;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.sem.account.dto.ReceivedDto;
import com.bee32.sem.account.entity.Received;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.service.PeopleService;

public class ReceivedAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public ReceivedAdminBean() {
        super(Received.class, ReceivedDto.class, 0, new Equals("class", "RED"));
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
