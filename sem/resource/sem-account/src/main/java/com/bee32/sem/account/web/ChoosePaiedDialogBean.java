package com.bee32.sem.account.web;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.sem.account.dto.PaiedDto;
import com.bee32.sem.account.entity.Paied;

public class ChoosePaiedDialogBean
        extends ChooseAccountAbleDialogBean {

    private static final long serialVersionUID = 1L;

    public ChoosePaiedDialogBean() {
        super(Paied.class, PaiedDto.class, 0, new Equals("class", "PED"));
    }

}
