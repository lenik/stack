package com.bee32.sem.asset.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.httpclient.methods.multipart.Part;

import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.asset.dto.AccountSubjectDto;
import com.bee32.sem.asset.dto.AccountTicketDto;
import com.bee32.sem.asset.dto.AccountTicketItemDto;
import com.bee32.sem.asset.dto.BudgetRequestDto;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.misc.EntityCriteria;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.util.PeopleCriteria;
import com.bee32.sem.world.monetary.CurrencyUtil;

public class AccountTicketAdminBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private Date limitDateFrom;
    private Date limitDateTo;

    private boolean editable = false;

    private int goNumber;
    private int count;

    protected AccountTicketDto accountTicket = new AccountTicketDto().create();

    protected AccountTicketItemDto accountTicketItem = new AccountTicketItemDto().create();

    private String budgetRequestPattern;
    private List<BudgetRequestDto> budgetRequests;
    private BudgetRequestDto selectedBudgetRequest;

    private String partyPattern;
    private List<PartyDto> parties;
    private PartyDto selectedParty;

    private String accountSubjectPattern;
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
                EntityCriteria.createdBetweenEx(limitDateFrom, limitDateTo));
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

    public String getAccountSubjectPattern() {
        return accountSubjectPattern;
    }

    public void setAccountSubjectPattern(String accountSubjectPattern) {
        this.accountSubjectPattern = accountSubjectPattern;
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
        //刷新总记录数
        getCount();

        goNumber = position;

        if(position < 1) {
            goNumber = 1;
            position = 1;
        }
        if(goNumber > count) {
            goNumber = count;
            position = count;
        }


        accountTicket = new AccountTicketDto().create();

        AccountTicket firstTicket = serviceFor(AccountTicket.class).getFirst( //
                new Offset(position - 1), //
                EntityCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                Order.asc("id"));

        if (firstTicket != null)
            accountTicket = DTOs.marshal(AccountTicketDto.class, AccountTicketDto.ITEMS, firstTicket);

    }

    public void new_() {
        accountTicket = new AccountTicketDto().create();
        editable = true;
    }

    public void modify() {
        if(accountTicket.getId() == null) {
            uiLogger.warn("当前没有对应的凭证");
            return;
        }

        itemsNeedToRemoveWhenModify.clear();

        editable = true;
    }

    public void save() {
        if(accountTicket.getId() == null) {
            //新增
            goNumber = count + 1;
        }

        try {
            AccountTicket _ticket = accountTicket.unmarshal();
            for(AccountTicketItemDto item : itemsNeedToRemoveWhenModify) {
                _ticket.removeItem(item.unmarshal());
            }

            serviceFor(AccountTicket.class).save(_ticket);
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
        if (partPattern != null && !partPattern.isEmpty()) {

            List<Part> _parts = serviceFor(Part.class).list(BomCriteria.findPartUseMaterialName(partPattern));

            parts = DTOs.mrefList(PartDto.class, _parts);
        }
    }

    public void chooseBudget() {
        makeOrderItem.setPart(selectedPart);

        selectedPart = null;
    }

    public void findParty() {
        if (partyPattern != null && !partyPattern.isEmpty()) {

            List<Party> _parties = serviceFor(Party.class).list( //
                    PeopleCriteria.customers(), //
                    new Or( //
                            new Like("name", "%" + partyPattern + "%"), //
                            new Like("fullName", "%" + partyPattern + "%")));

            parties = DTOs.marshalList(PartyDto.class, _parties);
        }
    }

    public void chooseParty() {
        accountTicketItem.setParty(selectedParty);
    }

    public void findAccountSubject() {
        List<AccountSubject> _subjects = serviceFor(AccountSubject.class).list(//
                Order.desc("createdDate"), //
                nceCriteria.subjectLike(chancePattern));

        subjects = DTOs.mrefList(AccountSubjectDto.class, 0, _subjects);
    }

    public void chooseAccountSubject() {
        accountTicketItem.setSubject(selectedAccountSubject);
    }
}
