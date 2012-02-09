package com.bee32.sem.asset.web;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.ox1.dict.PoNode;
import com.bee32.sem.asset.dto.AccountSubjectDto;
import com.bee32.sem.asset.dto.AccountTicketItemDto;
import com.bee32.sem.asset.entity.AccountTicketItem;
import com.bee32.sem.asset.service.AssetQueryOptions;
import com.bee32.sem.asset.service.IAssetQuery;
import com.bee32.sem.asset.service.SumNode;
import com.bee32.sem.asset.service.SumTree;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.world.monetary.MCValue;

public class AssetQueryBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    boolean all;
    AccountSubjectDto subject;
    PartyDto party;
    Date fromDate;
    Date toDate;

    TreeNode root;

    public AssetQueryBean() {
        all = false;
        fromDate = new Date();
        toDate = new Date();
    }

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
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

    public TreeNode getRoot() {
        return root;
    }

    public void query() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(toDate);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        AssetQueryOptions options = new AssetQueryOptions(cal.getTime());
        options.setRecursive(true);
        if (!all) {
            if (subject == null) {
                uiLogger.warn("没有选择科目.");
                return;
            }
            options.addSubject(subject.unmarshal());
        }
        if (party != null && party.getId() != null) {
            options.addParty(party.unmarshal());
        }
        options.setParties(null, true);

        SumTree tree = ctx.getBean(IAssetQuery.class).getSummary(options);

        SumNode rootNode = tree.getRoot();
        root = new DefaultTreeNode(rootNode.getKey(), null);

        loadSumNodeRecursive(rootNode, root);
    }

    void loadSumNodeRecursive(SumNode sumNode, TreeNode parentNode) {
        for (PoNode<?> poNode : sumNode.getChildren()) {
            SumNode node = (SumNode) poNode;

            BigDecimal total = node.getTotal();
            AccountTicketItem _item = new AccountTicketItem();
            _item.setSubject(node.getData());
            _item.setDebitSide(node.getData().isDebitSign());
            _item.setValue(new MCValue(total));

            TreeNode totalNode = new DefaultTreeNode(_item.getSubject().getId(), DTOs.marshal(
                    AccountTicketItemDto.class, _item), parentNode);

            List<AccountTicketItem> items = node.getItems();
            for (AccountTicketItem subItem : items) {
                new DefaultTreeNode(subItem.getSubject().getId(), DTOs.marshal(AccountTicketItemDto.class, subItem),
                        totalNode);
            }

            loadSumNodeRecursive(node, totalNode);
        }
    }
}
