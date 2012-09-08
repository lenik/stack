package com.bee32.sem.asset.web;

import java.util.List;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.asset.dto.AccountTicketDto;
import com.bee32.sem.asset.dto.AccountTicketItemDto;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.service.PeopleService;
import com.bee32.sem.world.monetary.FxrQueryException;

@ForEntity(AccountTicket.class)
public class AccountTicketAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    public AccountTicketAdminBean() {
        super(AccountTicket.class, AccountTicketDto.class, 0);
    }

    /**
     * 在页面上使用，使用户选择部门时只出现本公司的部门
     *
     * @return
     */
    public Integer getSelfOrgId() {
        return BEAN(PeopleService.class).getSelfOrg().getId();
    }

    public void findBudgetRequest() {
        // TODO : 加入业务单是否已经审核的条件检查
        // new Or(
        // new Equals("owner.id", budgetRequestCreatorId),
        // new Like("description", "%" + budgetRequestPattern + "%")),
        // AssetCriteria.haveNoCorrespondingTicket()
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    final ListMBean<AccountTicketItemDto> itemsMBean = ListMBean.fromEL(this, //
            "openedObject.items", AccountTicketItemDto.class);

    public ListMBean<AccountTicketItemDto> getItemsMBean() {
        return itemsMBean;
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/
    @Override
    protected boolean postValidate(List<?> dtos)
            throws FxrQueryException {
        for (Object dto : dtos) {
            AccountTicketDto ticket = (AccountTicketDto) dto;
            if (!ticket.isDebitCreditEqual()) {
                uiLogger.error("借贷金额不相等");
                return false;
            }
        }
        return true;
    }


}
