package com.bee32.sem.asset.service;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.access.annotation.AccessCheck;
import com.bee32.plover.arch.DataService;
import com.bee32.sem.asset.dto.PaymentNoteDto;
import com.bee32.sem.asset.entity.PaymentNote;

public class AssetService extends DataService {

    /**
     * 付款单上完成付款动作
     */
    @AccessCheck
    @Transactional
    public void pay(PaymentNoteDto note) {
        if (note.getWhoPay() == null) {
            throw new HaveNotWhoPayException();
        }
        PaymentNote _note = (PaymentNote) note.unmarshal();
        DATA(PaymentNote.class).saveOrUpdate(_note);
    }

}
