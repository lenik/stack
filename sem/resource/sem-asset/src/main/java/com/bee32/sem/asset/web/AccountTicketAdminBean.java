package com.bee32.sem.asset.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.asset.dto.AccountTicketDto;
import com.bee32.sem.asset.dto.AccountTicketItemDto;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.misc.ScrollEntityViewBean;

@ForEntity(AccountTicket.class)
public class AccountTicketAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    ListMBean<AccountTicketItemDto> itemsMBean = ListMBean.fromEL(this, "openedObject.items",
            AccountTicketItemDto.class);

    public AccountTicketAdminBean() {
        super(AccountTicket.class, AccountTicketDto.class, 0);
    }

    public void findBudgetRequest() {
        // TODO : 加入业务单是否已经审核的条件检查
        // new Or(
        // new Equals("owner.id", budgetRequestCreatorId),
        // new Like("description", "%" + budgetRequestPattern + "%")),
        // AssetCriteria.haveNoCorrespondingTicket()
    }

    public ListMBean<AccountTicketItemDto> getItemsMBean() {
        return itemsMBean;
    }

    public void setItemsMBean(ListMBean<AccountTicketItemDto> itemsMBean) {
        this.itemsMBean = itemsMBean;
    }

}
