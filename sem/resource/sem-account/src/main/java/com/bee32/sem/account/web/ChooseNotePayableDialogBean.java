package com.bee32.sem.account.web;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.sem.account.dto.NotePayableDto;
import com.bee32.sem.account.entity.NotePayable;

public class ChooseNotePayableDialogBean
        extends ChooseAccountEdDialogBean {

    private static final long serialVersionUID = 1L;

    public ChooseNotePayableDialogBean() {
        super(NotePayable.class, NotePayableDto.class, 0, new Equals("class", "PNOTE"));
    }

}
