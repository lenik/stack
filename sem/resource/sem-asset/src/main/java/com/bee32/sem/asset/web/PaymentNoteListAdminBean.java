package com.bee32.sem.asset.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.asset.dto.PaymentNoteDto;
import com.bee32.sem.asset.entity.PaymentNote;
import com.bee32.sem.misc.SimpleEntityViewBean;

@ForEntity(PaymentNote.class)
public class PaymentNoteListAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;


    public PaymentNoteListAdminBean() {
        super(PaymentNote.class, PaymentNoteDto.class, 0);
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
