package com.bee32.sem.asset.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.asset.dto.AccountTicketDto;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.misc.SimpleEntityViewBean;

@ForEntity(AccountTicket.class)
public class AccountTicketListAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;


    public AccountTicketListAdminBean() {
        super(AccountTicket.class, AccountTicketDto.class, AccountTicketDto.ITEMS);
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
