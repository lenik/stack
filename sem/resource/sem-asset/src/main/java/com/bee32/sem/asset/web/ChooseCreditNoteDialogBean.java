package com.bee32.sem.asset.web;

import com.bee32.sem.asset.dto.CreditNoteDto;
import com.bee32.sem.asset.entity.CreditNote;

public class ChooseCreditNoteDialogBean extends ChooseFundFlowDialogBean {

    private static final long serialVersionUID = 1L;

    public ChooseCreditNoteDialogBean() {
        super(CreditNote.class, CreditNoteDto.class, 0);
    }
}
