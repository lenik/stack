package com.bee32.sem.asset.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.asset.dto.AccountSubjectDto;
import com.bee32.sem.asset.dto.AccountTicketDto;
import com.bee32.sem.asset.dto.AccountTicketItemDto;
import com.bee32.sem.asset.dto.BudgetRequestDto;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.people.dto.PartyDto;

@ForEntity(AccountTicket.class)
public class AccountTicketAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    protected AccountTicketDto accountTicket = new AccountTicketDto().create();
    protected AccountTicketItemDto accountTicketItem = new AccountTicketItemDto().create();

    private int budgetRequestCreatorId;
    private BudgetRequestDto selectedBudgetRequest;

    private PartyDto selectedParty;

    private AccountSubjectDto selectedAccountSubject;

    private boolean newItemStatus = false;

    protected List<AccountTicketItemDto> itemsNeedToRemoveWhenModify = new ArrayList<AccountTicketItemDto>();

    public AccountTicketAdminBean() {
        super(AccountTicket.class, AccountTicketDto.class, 0);
    }

    public AccountTicketDto getAccountTicket() {
        return accountTicket;
    }

    public void setAccountTicket(AccountTicketDto accountTicket) {
        this.accountTicket = accountTicket;
    }

    public String getCreator() {
        if (accountTicket == null)
            return "";
        else
            return accountTicket.getOwnerDisplayName();
    }

    public List<AccountTicketItemDto> getItems() {
        if (accountTicket == null)
            return null;
        return accountTicket.getItems();
    }

    public AccountTicketItemDto getAccountTicketItem() {
        return accountTicketItem;
    }

    public void setAccountTicketItem(AccountTicketItemDto accountTicketItem) {
        this.accountTicketItem = accountTicketItem;
    }

    public int getBudgetRequestCreatorId() {
        return budgetRequestCreatorId;
    }

    public void setBudgetRequestCreatorId(int budgetRequestCreatorId) {
        this.budgetRequestCreatorId = budgetRequestCreatorId;
    }

    public BudgetRequestDto getSelectedBudgetRequest() {
        return selectedBudgetRequest;
    }

    public void setSelectedBudgetRequest(BudgetRequestDto selectedBudgetRequest) {
        this.selectedBudgetRequest = selectedBudgetRequest;
    }

    public PartyDto getSelectedParty() {
        return selectedParty;
    }

    public void setSelectedParty(PartyDto selectedParty) {
        this.selectedParty = selectedParty;
    }

    public AccountSubjectDto getSelectedAccountSubject() {
        return selectedAccountSubject;
    }

    public void setSelectedAccountSubject(AccountSubjectDto selectedAccountSubject) {
        this.selectedAccountSubject = selectedAccountSubject;
    }

    public boolean isNewItemStatus() {
        return newItemStatus;
    }

    public void setNewItemStatus(boolean newItemStatus) {
        this.newItemStatus = newItemStatus;
    }

    private void loadAccountTicket(int position) {
        // 刷新总记录数

        accountTicket = new AccountTicketDto().create();

        AccountTicket firstTicket = serviceFor(AccountTicket.class).getFirst( //
                new Offset(position - 1), //
// CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                Order.asc("id"));

        if (firstTicket != null)
            accountTicket = DTOs.marshal(AccountTicketDto.class, AccountTicketDto.ITEMS, firstTicket);

    }

    public void modify() {
        if (accountTicket.getId() == null) {
            uiLogger.warn("当前没有对应的凭证");
            return;
        }

        itemsNeedToRemoveWhenModify.clear();
    }

    @Transactional
    public void save1() {
        if (accountTicket.getId() == null) {
            // 新增
// goNumber = count + 1;
        }

        try {
            AccountTicket _ticket = accountTicket.unmarshal();
            if (!_ticket.isDebitCreditEqual()) {
                uiLogger.warn("借贷不相等");
                return;
            }

            for (AccountTicketItemDto item : itemsNeedToRemoveWhenModify) {
                _ticket.removeItem(item.unmarshal());
            }

            serviceFor(AccountTicket.class).saveOrUpdate(_ticket);
            uiLogger.info("保存成功");
// loadAccountTicket(goNumber);
        } catch (Exception e) {
            uiLogger.warn("保存失败.", e);
        }
    }

    public void newItem() {
        accountTicketItem = new AccountTicketItemDto().create();
        accountTicketItem.setTicket(accountTicket);

        newItemStatus = true;
    }

    public void modifyItem() {
        newItemStatus = false;
    }

    public void saveItem() {
        accountTicketItem.setTicket(accountTicket);
        if (newItemStatus) {
            accountTicket.addItem(accountTicketItem);
        }
    }

    public void delete() {
        try {
            serviceFor(AccountTicket.class).delete(accountTicket.unmarshal());
            uiLogger.info("删除成功!");
// loadAccountTicket(goNumber);
        } catch (Exception e) {
            uiLogger.warn("删除失败.", e);
        }
    }

    public void deleteItem() {
        accountTicket.removeItem(accountTicketItem);

        if (accountTicketItem.getId() != null) {
            itemsNeedToRemoveWhenModify.add(accountTicketItem);
        }
    }

    public void findBudgetRequest() {
        // TODO : 加入业务单是否已经审核的条件检查
// new Or(
// new Equals("owner.id", budgetRequestCreatorId),
// new Like("description", "%" + budgetRequestPattern + "%")),
// AssetCriteria.haveNoCorrespondingTicket()
    }

    public void chooseBudgetRequest() {
        accountTicket.setRequest(selectedBudgetRequest);

        selectedBudgetRequest = null;
    }

    public void chooseParty() {
        accountTicketItem.setParty(selectedParty);
    }

    public void findAccountSubject() {
        // 在实体中,name代表科目代码，label代表科目名称
// AssetCriteria.subjectLike(accountSubjectCodePattern, accountSubjectNamePattern));
    }

    public void chooseAccountSubject() {
        accountTicketItem.setSubject(selectedAccountSubject);
    }

    @Override
    public List<?> getSelection() {
        return listOfNonNulls(accountTicket);
    }

}
