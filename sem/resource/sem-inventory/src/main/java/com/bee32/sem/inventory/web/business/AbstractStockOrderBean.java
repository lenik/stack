package com.bee32.sem.inventory.web.business;

import java.util.List;

import javax.inject.Inject;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.inventory.tx.dto.StockJobDto;
import com.bee32.sem.inventory.util.StockCriteria;
import com.bee32.sem.inventory.util.StockJobStepping;
import com.bee32.sem.inventory.web.StockQueryDialogBean;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.dto.OrgUnitDto;

@ForEntity(StockOrder.class)
public abstract class AbstractStockOrderBean
        extends ScrollEntityViewBean
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    protected StockOrderSubject subject;
    StockJobStepping stepping;

    int selectedWarehouseId = -1;
    OrgDto selectedOrg;
    OrgUnitDto selectedOrgUnit;

    @Inject
    protected StockDictsBean dicts;

    public AbstractStockOrderBean() {
        super(StockOrder.class, StockOrderDto.class, 0);
        String s = ctx.view.getRequest().getParameter("subject");
        subject = s == null ? null : StockOrderSubject.forValue(s);

        stepping = new StockJobFriend();
        if (configJobStepping(stepping))
            addFriend("job", stepping);
    }

    class StockJobFriend
            extends StockJobStepping {

        private static final long serialVersionUID = 1L;

        @Override
        protected StockJobDto<?> createJob(StockOrderDto initiatorOrder)
                throws InstantiationException, IllegalAccessException {
            StockJobDto<?> stockJob = super.createJob(initiatorOrder);
            initStockJob(stockJob);
            return stockJob;
        }

    }

    protected abstract boolean configJobStepping(StockJobStepping stepping);

    public StockJobStepping getJob() {
        return stepping;
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        super.composeBaseRestrictions(elements);
        if (subject != null)
            elements.add(StockCriteria.subjectOf(subject));
        if (selectedWarehouseId != -1 || true) // get nothing if warehouse isn't selected:
            elements.add(new Equals("warehouse.id", selectedWarehouseId));
    }

    protected void initStockJob(StockJobDto<?> stockJob) {
    }

    public StockOrderSubject getSubject() {
        return subject;
    }

    public void setSubject(StockOrderSubject subject) {
        this.subject = subject;
    }

    public int getSelectedWarehouseId() {
        return selectedWarehouseId;
    }

    public void setSelectedWarehouseId(int selectedWarehouseId) {
        if (this.selectedWarehouseId != selectedWarehouseId) {
            this.selectedWarehouseId = selectedWarehouseId;
            refreshRowCount();
            selectRow(1);
        }
    }

    public StockWarehouseDto getSelectedWarehouse() {
        if (selectedWarehouseId == -1)
            return null;
        else
            return dicts.getWarehouse(selectedWarehouseId);
    }

    public OrgDto getSelectedOrg() {
        return selectedOrg;
    }

    public void setSelectedOrg(OrgDto selectedOrg) {
        this.selectedOrg = selectedOrg;
    }

    public OrgUnitDto getSelectedOrgUnit() {
        return selectedOrgUnit;
    }

    public void setSelectedOrgUnit(OrgUnitDto selectedOrgUnit) {
        this.selectedOrgUnit = selectedOrgUnit;
    }

    public StockQueryOptions getStockQueryOptions() {
        StockQueryOptions options = StockQueryDialogBean.buildQueryOptions(selectedWarehouseId);
        return options;
    }

    public void setStockQueryItemToApply(StockOrderItemDto stockQueryItem) {
        if (stockQueryItem == null) {
            uiLogger.warn("没有选定查询的项目。");
            return;
        }
        StockOrderItemDto orderItem = itemsMBean.getOpenedObject();
        orderItem.setMaterial(stockQueryItem.getMaterial());
        orderItem.setBatchArray(stockQueryItem.getBatchArray());
        orderItem.setPrice(stockQueryItem.getPrice());
        orderItem.setExpirationDate(stockQueryItem.getExpirationDate());
        orderItem.setLocation(stockQueryItem.getLocation());
        // itemsMBean.apply();
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    protected Class<? extends StockOrderItemDto> getItemDtoClass() {
        return StockOrderItemDto.class;
    }

    final ListMBean<? extends StockOrderItemDto> itemsMBean = ListMBean.fromEL(this, "openedObject.items",
            getItemDtoClass());

    public ListMBean<? extends StockOrderItemDto> getItemsMBean() {
        return itemsMBean;
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/
    @Override
    protected StockOrderDto create() {
        if (selectedWarehouseId == -1) {
            uiLogger.error("请选择对应的仓库!");
            return null;
        }
        if (subject == null) {
            uiLogger.error("非法使用：科目尚未确定");
            return null;
        }
        StockOrderDto order = (StockOrderDto) super.create();
        order.setWarehouse(getSelectedWarehouse());
        order.setSubject(subject);
        return order;
    }

    @Override
    public void showEditForm() {
        for (Object s : getSelection()) {
            StockOrderDto stockOrder = (StockOrderDto) s;
            if (stockOrder.getEntityFlags().isLocked()) {
                uiLogger.error("单据已经锁定，不能修改!");
                return;
            }
        }
        super.showEditForm();
    }

}
