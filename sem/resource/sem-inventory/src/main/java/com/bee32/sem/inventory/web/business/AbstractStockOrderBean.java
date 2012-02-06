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
import com.bee32.sem.inventory.util.StockCriteria;
import com.bee32.sem.inventory.web.ChooseStockItemDialogBean;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.dto.OrgUnitDto;

@ForEntity(StockOrder.class)
public abstract class AbstractStockOrderBean
        extends ScrollEntityViewBean
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    @Inject
    protected StockDictsBean dicts;

    StockOrderSubject subject = null;
    Integer selectedWarehouseId = -1;

    OrgDto selectedOrg;
    OrgUnitDto selectedOrgUnit;

    StockOrderItemDto selectedStockQueryItem;

    public AbstractStockOrderBean() {
        super(StockOrder.class, StockOrderDto.class, 0);
        String s = getRequest().getParameter("subject");
        subject = s == null ? null : StockOrderSubject.valueOf(s);
    }

    @Override
    protected void composeBaseCriteriaElements(List<ICriteriaElement> elements) {
        super.composeBaseCriteriaElements(elements);
        if (subject != null)
            elements.add(StockCriteria.subjectOf(subject));
        if (selectedWarehouseId != -1 || true) // get nothing if warehouse isn't selected:
            elements.add(new Equals("warehouse.id", selectedWarehouseId));
    }

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
        StockQueryOptions options = ChooseStockItemDialogBean.buildQueryOptions(selectedWarehouseId);
        return options;
    }

    public StockOrderItemDto getSelectedStockQueryItem() {
        return selectedStockQueryItem;
    }

    public void setSelectedStockQueryItem(StockOrderItemDto selectedStockQueryItem) {
        this.selectedStockQueryItem = selectedStockQueryItem;
    }

    public void applyStockOrderItem() {
        StockOrderItemDto orderItem = itemsMBean.getOpenedObject();
        orderItem.setBatch(selectedStockQueryItem.getBatch());
        orderItem.setExpirationDate(selectedStockQueryItem.getExpirationDate());
        orderItem.setLocation(selectedStockQueryItem.getLocation());
    }

    ListMBean<StockOrderItemDto> itemsMBean = ListMBean.fromEL(this, "openedObject.items", StockOrderItemDto.class);

    public ListMBean<? extends StockOrderItemDto> getItemsMBean() {
        return itemsMBean;
    }

}
