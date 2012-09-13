package com.bee32.sem.asset.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.asset.dto.CreditNoteDto;
import com.bee32.sem.asset.entity.CreditNote;
import com.bee32.sem.asset.entity.FundFlow;
import com.bee32.sem.misc.ScrollEntityViewBean;

@ForEntity(FundFlow.class)
public class CreditNoteAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    public CreditNoteAdminBean() {
        super(CreditNote.class, CreditNoteDto.class, 0);
    }

}
