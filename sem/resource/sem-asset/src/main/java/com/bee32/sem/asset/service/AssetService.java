package com.bee32.sem.asset.service;

import com.bee32.icsf.access.annotation.AccessCheck;
import com.bee32.plover.arch.DataService;
import com.bee32.sem.asset.dto.PaymentNoteDto;
import com.bee32.sem.asset.entity.PaymentNote;
import com.bee32.sem.people.dto.PersonDto;

public class AssetService extends DataService {

    /**
     * 付款单上完成付款动作
     */
    @AccessCheck
    public void payment(PaymentNoteDto note, PersonDto whoPay) {
        note.setWhoPay(whoPay);
        PaymentNote _note = (PaymentNote) note.unmarshal();
        DATA(PaymentNote.class).saveOrUpdate(_note);
    }

}
