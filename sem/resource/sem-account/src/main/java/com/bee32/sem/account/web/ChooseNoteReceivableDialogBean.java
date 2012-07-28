package com.bee32.sem.account.web;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.sem.account.dto.NoteReceivableDto;
import com.bee32.sem.account.entity.NoteReceivable;

public class ChooseNoteReceivableDialogBean
        extends ChooseAccountEdDialogBean {

    private static final long serialVersionUID = 1L;

    public ChooseNoteReceivableDialogBean() {
        super(NoteReceivable.class, NoteReceivableDto.class, 0, new Equals("class", "RNOTE"));
    }

}
