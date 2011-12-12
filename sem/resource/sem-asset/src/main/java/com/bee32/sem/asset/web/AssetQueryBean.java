package com.bee32.sem.asset.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.asset.dto.AccountSubjectDto;
import com.bee32.sem.asset.dto.AccountTicketItemDto;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.asset.entity.AccountTicketItem;
import com.bee32.sem.asset.service.AssetQueryOptions;
import com.bee32.sem.asset.service.IAssetQuery;
import com.bee32.sem.asset.service.SumNode;
import com.bee32.sem.asset.service.SumTree;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;

public class AssetQueryBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    AccountSubjectDto subject;
    PartyDto party;
    Date fromDate;
    Date toDate;

    private String partyPattern;
    private List<PartyDto> parties;
    private PartyDto selectedParty;

    private String accountSubjectCodePattern;
    private String accountSubjectNamePattern;
    private List<AccountSubjectDto> accountSubjects;
    private AccountSubjectDto selectedAccountSubject;

    private TreeNode root;

    public AssetQueryBean() {
        fromDate = new Date();
        toDate = new Date();
    }

    public AccountSubjectDto getSubject() {
        return subject;
    }

    public void setSubject(AccountSubjectDto subject) {
        this.subject = subject;
    }

    public PartyDto getParty() {
        return party;
    }

    public void setParty(PartyDto party) {
        this.party = party;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
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

    public TreeNode getRoot() {
        return root;
    }





    public void findParty() {
        if (partyPattern != null && !partyPattern.isEmpty()) {

            List<Party> _parties = serviceFor(Party.class).list( //
                    new Or( //
                            new Like("name", "%" + partyPattern + "%"), //
                            new Like("fullName", "%" + partyPattern + "%")));

            parties = DTOs.mrefList(PartyDto.class, _parties);
        }
    }

    public void chooseParty() {
        party = selectedParty;
    }

    public void findAccountSubject() {
        //在实体中,name代表科目代码，label代表科目名称
        List<AccountSubject> _subjects = serviceFor(AccountSubject.class).list(//
                new Like("id", "%" + accountSubjectCodePattern + "%"),
                new Like("label", "%" + accountSubjectNamePattern + "%"));

        accountSubjects = DTOs.mrefList(AccountSubjectDto.class, 0, _subjects);
    }

    public void chooseAccountSubject() {
        subject = selectedAccountSubject;
    }

    public void query() {
        if (subject == null) {
            uiLogger.warn("没有选择科目.");
            return;
        }


        AssetQueryOptions options = new AssetQueryOptions(toDate);
        options.addSubject(subject.unmarshal());
        if (party != null) {
            options.addParty(party.unmarshal());
        }
        options.setParties(null, true);

        SumTree tree = getBean(IAssetQuery.class).getSummary(options);

        SumNode rootNode = tree.getRoot();
        root = new DefaultTreeNode(rootNode.getKey(), null);

        loadSumNodeRecursive(rootNode, root);
    }

    void loadSumNodeRecursive(SumNode sumNode, TreeNode parentNode) {
        BigDecimal total = sumNode.getTotal();
        AccountTicketItem _item = new AccountTicketItem();
        _item.setSubject(sumNode.getData());


        List<AccountTicketItem> items = sumNode.getItems();

        for(SumNode node : sumNode.getChildren()) {

        }




    }


}
