package com.bee32.sem.asset.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.asset.dto.BudgetRequestDto;
import com.bee32.sem.asset.entity.BudgetRequest;
import com.bee32.sem.world.monetary.CurrencyUtil;

@ForEntity(BudgetRequest.class)
public class BudgetRequestAdminBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private Date limitDateFrom;
    private Date limitDateTo;

    private boolean editable = false;

    private int goNumber;
    private int count;

    private BudgetRequestDto budgetRequest = new BudgetRequestDto().create();

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
                CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo));
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

        budgetRequest = new BudgetRequestDto().create();

        BudgetRequest firstRequest = serviceFor(BudgetRequest.class).getFirst( //
                new Offset(position - 1), //
                CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                Order.asc("id")//
                );

        if (firstRequest != null)
            budgetRequest = DTOs.marshal(BudgetRequestDto.class, firstRequest);
    }

    public void new_() {
        budgetRequest = new BudgetRequestDto().create();
        editable = true;
    }

    public void modify() {
        if (budgetRequest.getId() == null) {
            uiLogger.warn("当前没有对应的单据");
            return;
        }

        editable = true;
    }

    public void save() {
        if (budgetRequest.getId() == null) {
            // 新增
            goNumber = count + 1;
        }

        try {
            BudgetRequest _request = budgetRequest.unmarshal();

            serviceFor(BudgetRequest.class).saveOrUpdate(_request);
            uiLogger.info("保存成功");
            loadBudgetRequest(goNumber);
            editable = false;
        } catch (Exception e) {
            uiLogger.warn("保存失败.", e);
        }
    }

    public void cancel() {
        loadBudgetRequest(goNumber);
        editable = false;
    }

    public void first() {
        goNumber = 1;
        loadBudgetRequest(goNumber);
    }

    public void previous() {
        goNumber--;
        if (goNumber < 1)
            goNumber = 1;
        loadBudgetRequest(goNumber);
    }

    public void go() {
        if (goNumber < 1) {
            goNumber = 1;
        } else if (goNumber > count) {
            goNumber = count;
        }
        loadBudgetRequest(goNumber);
    }

    public void next() {
        goNumber++;

        if (goNumber > count)
            goNumber = count;
        loadBudgetRequest(goNumber);
    }

    public void last() {
        goNumber = count + 1;
        loadBudgetRequest(goNumber);
    }

    public void newItem() {
        budgetRequest = new BudgetRequestDto().create();
        newItemStatus = true;
    }

    public void modifyItem() {
        newItemStatus = false;
    }

    public void delete() {
        try {
            serviceFor(BudgetRequest.class).delete(budgetRequest.unmarshal());
            uiLogger.info("删除成功!");
            loadBudgetRequest(goNumber);
        } catch (Exception e) {
            uiLogger.warn("删除失败.", e);
        }
    }

}
