package com.bee32.sem.asset.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.principal.User;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.asset.dto.AccountSubjectDto;
import com.bee32.sem.asset.dto.AccountTicketDto;
import com.bee32.sem.asset.dto.AccountTicketItemDto;
import com.bee32.sem.asset.dto.BudgetRequestDto;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.asset.entity.BudgetRequest;
import com.bee32.sem.asset.util.AssetCriteria;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.util.PeopleCriteria;
import com.bee32.sem.world.monetary.CurrencyUtil;

@ForEntity(AccountTicket.class)
public class AccountTicketAdminBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private Date limitDateFrom;
    private Date limitDateTo;

    private boolean editable = false;

    private int goNumber;
    private int count;

    protected AccountTicketDto accountTicket = new AccountTicketDto().create();

    protected AccountTicketItemDto accountTicketItem = new AccountTicketItemDto().create();

    private int budgetRequestCreatorId;
    private String budgetRequestPattern;
    private List<BudgetRequestDto> budgetRequests;
    private BudgetRequestDto selectedBudgetRequest;

    private String partyPattern;
    private List<PartyDto> parties;
    private PartyDto selectedParty;

    private String accountSubjectCodePattern;
    private String accountSubjectNamePattern;
    private List<AccountSubjectDto> accountSubjects;
    private AccountSubjectDto selectedAccountSubject;

    private boolean newItemStatus = false;

    protected List<AccountTicketItemDto> itemsNeedToRemoveWhenModify = new ArrayList<AccountTicketItemDto>();

    public AccountTicketAdminBean() {
        Calendar c = Calendar.getInstance();
        // 取这个月的第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        limitDateFrom = c.getTime();

        // 最这个月的最后一天
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        limitDateTo = c.getTime();

        goNumber = 1;
        loadAccountTicket(goNumber);
    }

    public Date getLimitDateFrom() {
        return limitDateFrom;
    }

    public void setLimitDateFrom(Date limitDateFrom) {
        this.limitDateFrom = limitDateFrom;
    }

    public Date getLimitDateTo() {
        return limitDateTo;
    }

    public void setLimitDateTo(Date limitDateTo) {
        this.limitDateTo = limitDateTo;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public int getGoNumber() {
        return goNumber;
    }

    public void setGoNumber(int goNumber) {
        this.goNumber = goNumber;
    }

    public int getCount() {
        count = serviceFor(AccountTicket.class).count(//
                CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo));
        return count;
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

    public List<SelectItem> getCurrencies() {
        return CurrencyUtil.selectItems();
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<SelectItem> getUsers() {
        List<SelectItem> result = new ArrayList<SelectItem>();

        List<User> allUser = serviceFor(User.class).list();
        for (User u : allUser) {
            SelectItem userItem = new SelectItem();

            userItem.setValue(u.getId());
            userItem.setLabel(u.getDisplayName());

            result.add(userItem);
        }

        return result;
    }

    public int getBudgetRequestCreatorId() {
        return budgetRequestCreatorId;
    }

    public void setBudgetRequestCreatorId(int budgetRequestCreatorId) {
        this.budgetRequestCreatorId = budgetRequestCreatorId;
    }

    public String getBudgetRequestPattern() {
        return budgetRequestPattern;
    }

    public void setBudgetRequestPattern(String budgetRequestPattern) {
        this.budgetRequestPattern = budgetRequestPattern;
    }

    public List<BudgetRequestDto> getBudgetRequests() {
        return budgetRequests;
    }

    public void setBudgetRequests(List<BudgetRequestDto> budgetRequests) {
        this.budgetRequests = budgetRequests;
    }

    public BudgetRequestDto getSelectedBudgetRequest() {
        return selectedBudgetRequest;
    }

    public void setSelectedBudgetRequest(BudgetRequestDto selectedBudgetRequest) {
        this.selectedBudgetRequest = selectedBudgetRequest;
    }

    public String getPartyPattern() {
        return partyPattern;
    }

    public void setPartyPattern(String partyPattern) {
        this.partyPattern = partyPattern;
    }

    public List<PartyDto> getParties() {
        return parties;
    }

    public void setParties(List<PartyDto> parties) {
        this.parties = parties;
    }

    public PartyDto getSelectedParty() {
        return selectedParty;
    }

    public void setSelectedParty(PartyDto selectedParty) {
        this.selectedParty = selectedParty;
    }

    public String getAccountSubjectCodePattern() {
        return accountSubjectCodePattern;
    }

    public void setAccountSubjectCodePattern(String accountSubjectCodePattern) {
        this.accountSubjectCodePattern = accountSubjectCodePattern;
    }

    public String getAccountSubjectNamePattern() {
        return accountSubjectNamePattern;
    }

    public void setAccountSubjectNamePattern(String accountSubjectNamePattern) {
        this.accountSubjectNamePattern = accountSubjectNamePattern;
    }

    public List<AccountSubjectDto> getAccountSubjects() {
        return accountSubjects;
    }

    public void setAccountSubjects(List<AccountSubjectDto> accountSubjects) {
        this.accountSubjects = accountSubjects;
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

    public void limit() {
        loadAccountTicket(goNumber);
    }

    private void loadAccountTicket(int position) {
        // 刷新总记录数
        getCount();

        goNumber = position;

        if (position < 1) {
            goNumber = 1;
            position = 1;
        }
        if (goNumber > count) {
            goNumber = count;
            position = count;
        }

        accountTicket = new AccountTicketDto().create();

        AccountTicket firstTicket = serviceFor(AccountTicket.class).getFirst( //
                new Offset(position - 1), //
                CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                Order.asc("id"));

        if (firstTicket != null)
            accountTicket = DTOs.marshal(AccountTicketDto.class, AccountTicketDto.ITEMS, firstTicket);

    }

    public void new_() {
        accountTicket = new AccountTicketDto().create();
        editable = true;
    }

    public void modify() {
        if (accountTicket.getId() == null) {
            uiLogger.warn("当前没有对应的凭证");
            return;
        }

        itemsNeedToRemoveWhenModify.clear();

        editable = true;
    }

    @Transactional
    public void save() {
        if (accountTicket.getId() == null) {
            // 新增
            goNumber = count + 1;
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
            loadAccountTicket(goNumber);
            editable = false;
        } catch (Exception e) {
            uiLogger.warn("保存失败.", e);
        }
    }

    public void cancel() {
        loadAccountTicket(goNumber);
        editable = false;
    }

    public void first() {
        goNumber = 1;
        loadAccountTicket(goNumber);
    }

    public void previous() {
        goNumber--;
        if (goNumber < 1)
            goNumber = 1;
        loadAccountTicket(goNumber);
    }

    public void go() {
        if (goNumber < 1) {
            goNumber = 1;
        } else if (goNumber > count) {
            goNumber = count;
        }
        loadAccountTicket(goNumber);
    }

    public void next() {
        goNumber++;

        if (goNumber > count)
            goNumber = count;
        loadAccountTicket(goNumber);
    }

    public void last() {
        goNumber = count + 1;
        loadAccountTicket(goNumber);
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
            loadAccountTicket(goNumber);
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

        List<BudgetRequest> requests = serviceFor(BudgetRequest.class).list(
                new Equals("owner.id", budgetRequestCreatorId),
                new Like("description", "%" + budgetRequestPattern + "%"), AssetCriteria.haveNoCorrespondingTicket());

        budgetRequests = DTOs.marshalList(BudgetRequestDto.class, requests);
    }

    public void chooseBudgetRequest() {
        accountTicket.setRequest(selectedBudgetRequest);

        selectedBudgetRequest = null;
    }

    public void findParty() {
        if (partyPattern != null && !partyPattern.isEmpty()) {
            List<Party> _parties = serviceFor(Party.class).list(PeopleCriteria.namedLike(partyPattern));
            parties = DTOs.marshalList(PartyDto.class, _parties);
        }
    }

    public void chooseParty() {
        accountTicketItem.setParty(selectedParty);
    }

    public void findAccountSubject() {
        // 在实体中,name代表科目代码，label代表科目名称
        List<AccountSubject> _subjects = serviceFor(AccountSubject.class).list(//
                AssetCriteria.subjectLike(accountSubjectCodePattern, accountSubjectNamePattern));

        accountSubjects = DTOs.mrefList(AccountSubjectDto.class, 0, _subjects);
    }

    public void chooseAccountSubject() {
        accountTicketItem.setSubject(selectedAccountSubject);
    }

}
