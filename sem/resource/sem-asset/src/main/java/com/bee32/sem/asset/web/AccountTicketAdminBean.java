package com.bee32.sem.asset.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.asset.dto.AccountSubjectDto;
import com.bee32.sem.asset.dto.AccountTicketDto;
import com.bee32.sem.asset.dto.AccountTicketItemDto;
import com.bee32.sem.asset.dto.AccountTicketSource;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.asset.entity.IAccountTicketSource;
import com.bee32.sem.asset.util.AssetCriteria;
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
        AccountTicketSource ticketSource = new AccountTicketSource();
        ticketSource.setId(_source.getTicketSrcId());
        ticketSource.setLabel(_source.getTicketSrcLabel());
        ticketSource.setType(_source.getTicketSrcType());
        try {
            ticketSource.setValue(_source.getTicketSrcValue());
        } catch (FxrQueryException e1) {
            throw new RuntimeException(e1);
        }
        ticketSource.setClassType((Class<? extends Entity<?>>) _source.getClass());

        accountTicket.setTicketSource(ticketSource);

        // 把取到的凭证源上的金额生成凭证的一条明细
        AccountTicketItemDto item = new AccountTicketItemDto().create();
        item.setDescription(_source.getTicketSrcLabel());
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

            List<AccountTicketItemDto> items = ticket.getItems();
            for (AccountTicketItemDto item : items) {
                if (StringUtils.isEmpty(item.getDescription())) {
                    uiLogger.error("摘要不能为空");
                    return false;
                }
            }

            for (AccountTicketItemDto item : items) {
                AccountSubjectDto subject = item.getSubject();
                String name = subject.getName();
                List<AccountSubject> list = DATA(AccountSubject.class).list(AssetCriteria.subjectWithPrefix(name));
                if (list.size() != 1) {
                    uiLogger.warn(subject.getLabel() + "不是末级科目");
                    return false;
                }
            }
        }
        return true;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        for (AccountTicket me : uMap.<AccountTicket> entitySet()) {
            AccountTicketDto dto = uMap.getSourceDto(me);
            Class ticketSourceType = dto.getTicketSource().getClassType();
            IAccountTicketSource ticketSource = (IAccountTicketSource) DATA(ticketSourceType).get(
                    dto.getTicketSource().getId());
            ticketSource.setTicket(me);
            try {
                DATA(ticketSourceType).saveOrUpdate(ticketSource);
            } catch (Exception e) {
                uiLogger.error("无法关联凭证", e);
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected boolean preDelete(UnmarshalMap uMap)
            throws Exception {
        for (AccountTicket me : uMap.<AccountTicket> entitySet()) {
            AccountTicketDto dto = uMap.getSourceDto(me);

            AccountTicketSource tsHolder = dto.getTicketSource();
            if(tsHolder == null) {
                //凭证没有对应的凭证源，可以直接删除，所以直接返回true
                return true;
            }

            Class ticketSourceType = tsHolder.getClassType();

            IAccountTicketSource ticketSource = (IAccountTicketSource) DATA(ticketSourceType).get(
                    dto.getTicketSource().getId());
            ticketSource.setTicket(null);
            try {
                DATA(ticketSourceType).saveOrUpdate(ticketSource);
            } catch (Exception e) {
                uiLogger.error("...", e);
            }
        }

        return true;
    }

}
