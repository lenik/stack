package com.bee32.sem.account.web;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.sem.account.dto.ReceivedDto;
import com.bee32.sem.account.entity.Received;

public class ChooseReceivedDialogBean
        extends ChooseAccountAbleDialogBean {

    private static final long serialVersionUID = 1L;

    public ChooseReceivedDialogBean() {
        super(Received.class, ReceivedDto.class, 0, new Equals("class", "RED"));
    }

}
