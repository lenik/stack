package com.bee32.sem.asset.web;

import com.bee32.sem.asset.dto.PaymentNoteDto;
import com.bee32.sem.asset.entity.PaymentNote;

public class ChoosePaymentNoteDialogBean extends ChooseFundFlowDialogBean {

    private static final long serialVersionUID = 1L;

    public ChoosePaymentNoteDialogBean() {
        super(PaymentNote.class, PaymentNoteDto.class, 0);
    }
}
