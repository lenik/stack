package com.bee32.sem.asset.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.asset.dto.PaymentNoteDto;
import com.bee32.sem.asset.entity.FundFlow;
import com.bee32.sem.asset.entity.PaymentNote;
import com.bee32.sem.asset.service.AssetService;
import com.bee32.sem.misc.ScrollEntityViewBean;

@ForEntity(FundFlow.class)
public class PaymentNoteAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    public PaymentNoteAdminBean() {
        super(PaymentNote.class, PaymentNoteDto.class, 0);
    }

    public void pay() {
        PaymentNoteDto note =  getOpenedObject();
        BEAN(AssetService.class).pay(note);
    }

}
