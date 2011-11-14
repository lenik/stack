package com.bee32.sem.asset.web;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;
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
import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.sem.asset.dto.BudgetRequestDto;
import com.bee32.sem.asset.entity.BudgetRequest;
import com.bee32.sem.misc.EntityCriteria;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.util.PeopleCriteria;
import com.bee32.sem.world.monetary.CurrencyUtil;
import com.bee32.sem.world.monetary.MCValue;

public class BudgetRequestAdminBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private Date limitDateFrom;
    private Date limitDateTo;

    private boolean editable = false;

    private int goNumber;
    private int count;

    protected BudgetRequestDto budgetRequest = new BudgetRequestDto().create();


    private BigDecimal value = new BigDecimal(0);
    private Currency valueCurrency = CurrencyConfig.getNative();

    private boolean newItemStatus = false;

    public BudgetRequestAdminBean() {
        Calendar c = Calendar.getInstance();
        // 取这个月的第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        limitDateFrom = c.getTime();

        // 最这个月的最后一天
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        limitDateTo = c.getTime();

        goNumber = 1;
        loadBudgetRequest(goNumber);
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
        count = serviceFor(BudgetRequest.class).count(//
                EntityCriteria.createdBetweenEx(limitDateFrom, limitDateTo));
        return count;
    }

    public BudgetRequestDto getBudgetRequest() {
        return budgetRequest;
    }

    public void setBudgetRequest(BudgetRequestDto budgetRequest) {
        this.budgetRequest = budgetRequest;
    }

    public String getCreator() {
        if (budgetRequest == null)
            return "";
        else
            return budgetRequest.getOwnerDisplayName();
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Currency getValueCurrency() {
        return valueCurrency;
    }

    public void setValueCurrency(Currency valueCurrency) {
        this.valueCurrency = valueCurrency;
    }

    public List<SelectItem> getCurrencies() {
        return CurrencyUtil.selectItems();
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isNewItemStatus() {
        return newItemStatus;
    }

    public void setNewItemStatus(boolean newItemStatus) {
        this.newItemStatus = newItemStatus;
    }







    public void limit() {
        loadBudgetRequest(goNumber);
    }

    private void loadBudgetRequest(int position) {
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


        budgetRequest = new BudgetRequestDto().create();

        BudgetRequest firstRequest = serviceFor(BudgetRequest.class).getFirst( //
                new Offset(position - 1), //
                EntityCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                Order.asc("id"));

        if (firstRequest != null)
            budgetRequest = DTOs.marshal(BudgetRequestDto.class, firstRequest);

    }

    public void new_() {
        budgetRequest = new BudgetRequestDto().create();
        editable = true;
    }

    public void modify() {
        if(budgetRequest.getId() == null) {
            uiLogger.warn("当前没有对应的单据");
            return;
        }

        editable = true;
    }

    public void save() {
        if(budgetRequest.getId() == null) {
            //新增
            goNumber = count + 1;
        }

        try {
            BudgetRequest _request = budgetRequest.unmarshal();

            serviceFor(BudgetRequest.class).save(_request);
            uiLogger.info("保存成功");
            loadBudgetRequest(goNumber);
            editable = false;
        } catch (Exception e) {
            uiLogger.warn("保存失败.", e);
        }
    }

    public void cancel() {
        loadMakeOrder(goNumber);
        editable = false;
    }



    public void first() {
        goNumber = 1;
        loadMakeOrder(goNumber);
    }

    public void previous() {
        goNumber--;
        if (goNumber < 1)
            goNumber = 1;
        loadMakeOrder(goNumber);
    }

    public void go() {
        if (goNumber < 1) {
            goNumber = 1;
        } else if (goNumber > count) {
            goNumber = count;
        }
        loadMakeOrder(goNumber);
    }

    public void next() {
        goNumber++;

        if (goNumber > count)
            goNumber = count;
        loadMakeOrder(goNumber);
    }

    public void last() {
        goNumber = count + 1;
        loadMakeOrder(goNumber);
    }

    public void newItem() {
        makeOrderItem = new MakeOrderItemDto().create();
        makeOrderItem.setOrder(makeOrder);
        makeOrderItemPrice = new BigDecimal(0);
        makeOrderItemPriceCurrency = CurrencyConfig.getNative();

        newItemStatus = true;
    }

    public void modifyItem() {
        newItemStatus = false;
    }


    public void saveItem() {
        makeOrderItem.setOrder(makeOrder);
        MCValue newPrice = new MCValue(makeOrderItemPriceCurrency, makeOrderItemPrice);
        makeOrderItem.setPrice(newPrice);
        if (newItemStatus) {
            makeOrder.addItem(makeOrderItem);
        }
    }

    public void delete() {
        try {
            serviceFor(MakeOrder.class).delete(makeOrder.unmarshal());
            uiLogger.info("删除成功!");
            loadMakeOrder(goNumber);
        } catch (Exception e) {
            uiLogger.warn("删除失败,错误信息:" + e.getMessage());
        }
    }

    public void deleteItem() {
        makeOrder.removeItem(makeOrderItem);

        if (makeOrderItem.getId() != null) {
            itemsNeedToRemoveWhenModify.add(makeOrderItem);
        }
    }

    public void findPart() {
        if (partPattern != null && !partPattern.isEmpty()) {

            List<Part> _parts = serviceFor(Part.class).list(BomCriteria.findPartUseMaterialName(partPattern));

            parts = DTOs.mrefList(PartDto.class, _parts);
        }
    }

    public void choosePart() {
        makeOrderItem.setPart(selectedPart);

        selectedPart = null;
    }

    public void findCustomer() {
        if (customerPattern != null && !customerPattern.isEmpty()) {

            List<Party> _customers = serviceFor(Party.class).list( //
                    PeopleCriteria.customers(), //
                    new Or( //
                            new Like("name", "%" + customerPattern + "%"), //
                            new Like("fullName", "%" + customerPattern + "%")));

            customers = DTOs.marshalList(PartyDto.class, _customers);
        }
    }

    public void chooseCustomer() {
        makeOrder.setCustomer(selectedCustomer);
    }

    public void findChance() {
        List<Chance> _chances = serviceFor(Chance.class).list(//
                Order.desc("createdDate"), //
                ChanceCriteria.subjectLike(chancePattern));

        chances = DTOs.mrefList(ChanceDto.class, 0, _chances);
    }

    public void chooseChance() {
        makeOrder.setChance(selectedChance);
    }
}
