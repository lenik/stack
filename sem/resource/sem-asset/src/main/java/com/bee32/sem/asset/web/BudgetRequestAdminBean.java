package com.bee32.sem.asset.web;

import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.asset.dto.BudgetRequestDto;
import com.bee32.sem.asset.entity.BudgetRequest;
import com.bee32.sem.misc.ScrollEntityViewBean;

@ForEntity(BudgetRequest.class)
public class BudgetRequestAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    private BudgetRequestDto budgetRequest = new BudgetRequestDto().create();

    private boolean newItemStatus = false;

    public BudgetRequestAdminBean() {
        super(BudgetRequest.class, BudgetRequestDto.class, 0);
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

    public boolean isNewItemStatus() {
        return newItemStatus;
    }

    public void setNewItemStatus(boolean newItemStatus) {
        this.newItemStatus = newItemStatus;
    }

    private void loadBudgetRequest(int position) {
        // 刷新总记录数

        budgetRequest = new BudgetRequestDto().create();

        BudgetRequest firstRequest = serviceFor(BudgetRequest.class).getFirst( //
                new Offset(position - 1), //
// CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                Order.asc("id")//
                );

        if (firstRequest != null)
            budgetRequest = DTOs.marshal(BudgetRequestDto.class, firstRequest);
    }

    public void modify() {
        if (budgetRequest.getId() == null) {
            uiLogger.warn("当前没有对应的单据");
            return;
        }
    }

    public void save1() {
        if (budgetRequest.getId() == null) {
            // 新增
// goNumber = count + 1;
        }

        try {
            BudgetRequest _request = budgetRequest.unmarshal();

            serviceFor(BudgetRequest.class).saveOrUpdate(_request);
            uiLogger.info("保存成功");
// loadBudgetRequest(goNumber);
        } catch (Exception e) {
            uiLogger.warn("保存失败.", e);
        }
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
// loadBudgetRequest(goNumber);
        } catch (Exception e) {
            uiLogger.warn("删除失败.", e);
        }
    }

}
