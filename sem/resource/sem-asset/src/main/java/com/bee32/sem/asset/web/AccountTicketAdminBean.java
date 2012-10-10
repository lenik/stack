package com.bee32.sem.asset.web;

import java.util.List;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.asset.dto.AccountTicketDto;
import com.bee32.sem.asset.dto.AccountTicketItemDto;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.asset.entity.IAccountTicketSource;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.service.PeopleService;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MutableMCValue;

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

    public void setSourceToApply(EntityDto<?, ?> source) {
        AccountTicketDto accountTicket = getOpenedObject();
        IAccountTicketSource _source = (IAccountTicketSource) source.unmarshal();
        accountTicket.setTicketSource(_source);

        //把取到的凭证源上的金额生成凭证的一条明细
        AccountTicketItemDto item = new AccountTicketItemDto().create();
        item.setLabel(_source.getTicketSrcLabel());
        item.setTicket(accountTicket);
        try {
            item.setValue(new MutableMCValue(_source.getTicketSrcValue()));
        } catch (FxrQueryException e) {
            item.setValue(new MutableMCValue());
            e.printStackTrace();
        }
        accountTicket.addItem(item);
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

    @Override
    protected void postUpdate(UnmarshalMap uMap) throws Exception {
        for (AccountTicketDto ticket : uMap.<AccountTicketDto> dtos()) {
            IAccountTicketSource ticketSource = ticket.getTicketSource();
            ticketSource.setTicket(ticket.unmarshal());
            DATA(Entity.class).saveOrUpdate(ticketSource);
        }
    }

    @Override
    protected boolean preDelete(UnmarshalMap uMap) throws Exception {
        // TODO Auto-generated method stub
        return super.preDelete(uMap);
    }


}
