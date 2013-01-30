package com.bee32.sem.asset.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.asset.dto.CreditNoteDto;
import com.bee32.sem.asset.entity.CreditNote;
import com.bee32.sem.misc.SimpleEntityViewBean;

@ForEntity(CreditNote.class)
public class CreditNoteListAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;


    public CreditNoteListAdminBean() {
        super(CreditNote.class, CreditNoteDto.class, 0);
    }


    /*************************************************************************
     * Section: MBeans
     *************************************************************************/



    /*************************************************************************
     * Section: Persistence
     *************************************************************************/


    /*************************************************************************
     * Section: Search
     *************************************************************************/

}
