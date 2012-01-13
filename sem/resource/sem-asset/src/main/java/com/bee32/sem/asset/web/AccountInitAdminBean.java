package com.bee32.sem.asset.web;

import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.criteria.hibernate.Not;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.asset.dto.AccountSubjectDto;
import com.bee32.sem.asset.dto.InitAccountTicketItemDto;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.asset.entity.InitAccountTicketItem;
import com.bee32.sem.asset.util.AssetCriteria;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.util.PeopleCriteria;
import com.bee32.sem.world.monetary.CurrencyUtil;

@ForEntity(InitAccountTicketItem.class)
public class AccountInitAdminBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    InitAccountTicketItemDto accountTicketItem = new InitAccountTicketItemDto().create();

    private String accountSubjectCodePattern;
    private String accountSubjectNamePattern;
    private List<AccountSubjectDto> accountSubjects;
    private AccountSubjectDto selectedAccountSubject;

    private String partyPattern;
    private List<PartyDto> parties;
    private PartyDto selectedParty;

    public List<SelectItem> getCurrencies() {
        return CurrencyUtil.selectItems();
    }

    public InitAccountTicketItemDto getAccountTicketItem() {
        return accountTicketItem;
    }

    public void setAccountTicketItem(InitAccountTicketItemDto accountTicketItem) {
        this.accountTicketItem = accountTicketItem;
    }

    public List<InitAccountTicketItemDto> getItems() {
        List<InitAccountTicketItem> _items = serviceFor(InitAccountTicketItem.class).list();
        return DTOs.marshalList(InitAccountTicketItemDto.class, _items);
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

    @Override
    public List<?> getSelection() {
        return getItems();
    }

    public AccountSubjectDto getSelectedAccountSubject() {
        return selectedAccountSubject;
    }

    public void setSelectedAccountSubject(AccountSubjectDto selectedAccountSubject) {
        this.selectedAccountSubject = selectedAccountSubject;
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

    public void newItem() {
        accountTicketItem = new InitAccountTicketItemDto().create();
        accountTicketItem.setDescription("初始化");
    }

    public void modifyItem() {

    }

    public void deleteItem() {
        try {
            InitAccountTicketItem _item = (InitAccountTicketItem) accountTicketItem.unmarshal();
            serviceFor(InitAccountTicketItem.class).delete(_item);
            uiLogger.info("删除成功.");
        } catch (Exception e) {
            uiLogger.error("删除失败.", e);
        }
    }

    public void saveItem() {
        try {
            InitAccountTicketItem _item = (InitAccountTicketItem) accountTicketItem.unmarshal();
            serviceFor(InitAccountTicketItem.class).saveOrUpdate(_item);
            uiLogger.info("保存成功.");
        } catch (Exception e) {
            uiLogger.error("保存失败.", e);
        }
    }

    public void findAccountSubject() {
        // 在实体中,name代表科目代码，label代表科目名称
        List<AccountSubject> _subjects = serviceFor(AccountSubject.class).list(//
                AssetCriteria.subjectLike(accountSubjectCodePattern, accountSubjectNamePattern));

        accountSubjects = DTOs.mrefList(AccountSubjectDto.class, 0, _subjects);
    }

    public void chooseAccountSubject() {
        // TODO 检测是否末级科目
        String name = selectedAccountSubject.getName();
        int subAccountSubjectCount = serviceFor(AccountSubject.class).count(new Like("id", "%" + name + "%"),
                new Not(new Equals("id", name)));
        if (subAccountSubjectCount > 0) {
            uiLogger.error("所选择科目不是末级科目!");
            return;
        }

        accountTicketItem.setSubject(selectedAccountSubject);
        accountTicketItem.setDebitSide(selectedAccountSubject.isDebitSign());
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




}
