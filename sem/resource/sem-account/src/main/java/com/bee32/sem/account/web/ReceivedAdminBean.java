package com.bee32.sem.account.web;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.sem.account.dto.ReceivedDto;
import com.bee32.sem.account.entity.Received;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ReceivedAdminBean extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public ReceivedAdminBean() {
        super(Received.class, ReceivedDto.class, 0, new Equals("class", "RED"));
    }

}
