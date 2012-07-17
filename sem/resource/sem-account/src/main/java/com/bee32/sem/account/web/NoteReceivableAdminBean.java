package com.bee32.sem.account.web;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.sem.account.dto.NoteReceivableDto;
import com.bee32.sem.account.entity.NoteReceivable;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.service.PeopleService;

public class NoteReceivableAdminBean extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public NoteReceivableAdminBean() {
        super(NoteReceivable.class, NoteReceivableDto.class, 0, new Equals("class", "RNOTE"));
    }

    /**
     * 在页面上使用，使用户选择部门时只出现本公司的部门
     * @return
     */
    public Integer getSelfOrgId() {
        return ctx.bean.getBean(PeopleService.class).getSelfOrg().getId();
    }

}
