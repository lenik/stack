package com.bee32.sem.account.web;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.sem.account.dto.PaiedDto;
import com.bee32.sem.account.entity.Paied;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class PaiedAdminBean extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public PaiedAdminBean() {
        super(Paied.class, PaiedDto.class, 0, new Equals("class", "PED"));
    }

}
