package com.bee32.sem.inventory.web.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockItemList;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.inventory.service.IStockQuery;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.dto.OrgUnitDto;
import com.bee32.sem.world.monetary.CurrencyUtil;

@ForEntity(StockOrder.class)
public abstract class AbstractStockOrderBean
        extends ScrollEntityViewBean
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    StockOrderSubject subject = null;
    int selectedWarehouseId = -1;

    OrgDto selectedOrg;
    OrgUnitDto selectedOrgUnit;
    private StockOrderItemDto selectedStockQueryItem;

    private String materialUnitId;

    public AbstractStockOrderBean() {
        super(StockOrder.class, StockOrderDto.class, 0);
    }

    public StockOrderSubject getSubject() {
        return subject;
    }

    @Override
    public List<?> getSelection() {
        return listOfNonNulls(stockOrder);
    }

    public String getCreator() {
        if (stockOrder == null)
            return "";
        else
            return stockOrder.getOwnerDisplayName();
    }

    public List<StockOrderItemDto> getItems() {
        if (stockOrder == null)
            return null;
        return stockOrder.getItems();
    }

    public StockOrderItemDto getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(StockOrderItemDto orderItem) {
        this.orderItem = orderItem;
    }

    public List<SelectItem> getCurrencies() {
        return CurrencyUtil.selectItems();
    }

    public boolean isNewItemStatus() {
        return newItemStatus;
    }

    public void setNewItemStatus(boolean newItemStatus) {
        this.newItemStatus = newItemStatus;
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

    public StockOrderItemDto getSelectedStockQueryItem() {
        return selectedStockQueryItem;
    }

    public void setSelectedStockQueryItem(StockOrderItemDto selectedStockQueryItem) {
        this.selectedStockQueryItem = selectedStockQueryItem;
    }

    public void findOrgUnit() {
// if (orgUnitPattern != null && !orgUnitPattern.isEmpty()) {
//
// List<OrgUnit> _orgUnits = null;
//
// if (stockOrder.getOrg().getId() != null) {
// // 如果前面选中了某个公司，则查找该公司中的部门
// _orgUnits = serviceFor(OrgUnit.class).list(new Like("name", "%" + orgUnitPattern + "%"),
// new Equals("org.id", selectedOrg.getId()));
// } else {
// // 如果没有选择公司，则表示查找tag为内部的公司中的部门(即为本公司内部的部门)
// _orgUnits =
// serviceFor(OrgUnit.class).list(PeopleCriteria.internalOrgUnitWithName(orgUnitPattern));
// }
//
// orgUnits = DTOs.mrefList(OrgUnitDto.class, _orgUnits);
// }
    }

    public List<StockOrderItemDto> getStockQueryItems() {
        if (selectedWarehouse.getId() == null)
            return new ArrayList<StockOrderItemDto>();

        if (orderItem.getMaterial() == null || orderItem.getMaterial().getId() == null)
            return new ArrayList<StockOrderItemDto>();

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);

        StockWarehouse w = selectedWarehouse.unmarshal();
        Material m = orderItem.getMaterial().unmarshal();

        List<Material> ms = new ArrayList<Material>();
        ms.add(m);

        StockQueryOptions opts = new StockQueryOptions(c.getTime());
        opts.setWarehouse(w);
        opts.setCBatch(null, true);
        opts.setLocation(null, true);

        IStockQuery q = getBean(IStockQuery.class);
        StockItemList list = q.getActualSummary(ms, opts);
        return DTOs.marshalList(StockOrderItemDto.class, list.getItems());
    }

    public void chooseStockQueryItem() {
        orderItem.setBatch(selectedStockQueryItem.getBatch());
        orderItem.setExpirationDate(selectedStockQueryItem.getExpirationDate());
        orderItem.setLocation(selectedStockQueryItem.getLocation());
    }

}
