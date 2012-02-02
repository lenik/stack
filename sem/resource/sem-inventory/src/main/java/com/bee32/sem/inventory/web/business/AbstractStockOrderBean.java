package com.bee32.sem.inventory.web.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
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
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.world.monetary.CurrencyUtil;
import com.bee32.sem.world.thing.UnitConvDto;
import com.bee32.sem.world.thing.UnitDto;

@ForEntity(StockOrder.class)
public abstract class AbstractStockOrderBean
        extends ScrollEntityViewBean
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    protected StockOrderSubject subject = null;
    protected StockWarehouseDto selectedWarehouse = new StockWarehouseDto().ref();
    protected boolean editable = false;

    protected StockOrderDto stockOrder = new StockOrderDto().create().ref();

    protected StockOrderItemDto orderItem = new StockOrderItemDto().create().ref();

    private boolean newItemStatus = false;

    private OrgDto selectedOrg;
    private OrgUnitDto selectedOrgUnit;

    private StockOrderItemDto selectedStockQueryItem;

    protected List<StockOrderItemDto> itemsNeedToRemoveWhenModify = new ArrayList<StockOrderItemDto>();

    private String materialUnitId;
    private BigDecimal materialQuantity;
    private BigDecimal materialPrice;

    public AbstractStockOrderBean() {
        super(StockOrder.class, StockOrderDto.class, 0);
    }

    public abstract StockOrderItemDto getOrderItem_();

    public abstract StockWarehouseDto getSelectedWarehouse_();

    public StockWarehouseDto getSelectedWarehouse() {
        return selectedWarehouse;
    }

    public void setSelectedWarehouse(StockWarehouseDto selectedWarehouse) {
        this.selectedWarehouse = selectedWarehouse;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public List<SelectItem> getStockWarehouses() {
        List<StockWarehouse> stockWarehouses = serviceFor(StockWarehouse.class).list();
        List<StockWarehouseDto> stockWarehouseDtos = DTOs.mrefList(StockWarehouseDto.class, stockWarehouses);

        List<SelectItem> items = new ArrayList<SelectItem>();

        for (StockWarehouseDto stockWarehouseDto : stockWarehouseDtos) {
            String label = stockWarehouseDto.getName();
            SelectItem item = new SelectItem(stockWarehouseDto.getId(), label);
            items.add(item);
        }

        return items;
    }

    public StockOrderSubject getSubject() {
        return subject;
    }

    public StockOrderDto getStockOrder() {
        return stockOrder;
    }

    public void setStockOrder(StockOrderDto stockOrder) {
        this.stockOrder = stockOrder;
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

    public String getMaterialUnitId() {
        return materialUnitId;
    }

    public void setMaterialUnitId(String materialUnitId) {
        this.materialUnitId = materialUnitId;
    }

    public List<SelectItem> getMaterialUnits() {
        if (orderItem.getMaterial() == null) {
            return new ArrayList<SelectItem>();
        }

        if (orderItem.getMaterial().getUnitConv() == null) {
            return new ArrayList<SelectItem>();
        }

        List<UnitDto> unitDtoList = new ArrayList<UnitDto>();
        UnitConvDto conv = orderItem.getMaterial().getUnitConv();
        if (conv.getScaleMap() != null) {
            for (UnitDto u : conv.getScaleMap().keySet()) {
                unitDtoList.add(u);
            }
        }

        return UIHelper.selectItemsFromDict2(unitDtoList);
    }

    public BigDecimal getMaterialQuantity() {
        return materialQuantity;
    }

    public void setMaterialQuantity(BigDecimal materialQuantity) {
        this.materialQuantity = materialQuantity;
    }

    public BigDecimal getMaterialPrice() {
        return materialPrice;
    }

    public void setMaterialPrice(BigDecimal materialPrice) {
        this.materialPrice = materialPrice;
    }

    public void newItem() {
        orderItem = new StockOrderItemDto().create();
        orderItem.setParent(stockOrder);
        newItemStatus = true;
    }

    public void modifyItem() {
        newItemStatus = false;
    }

    public void deleteItem() {
        stockOrder.removeItem(orderItem);

        if (orderItem.getId() != null) {
            itemsNeedToRemoveWhenModify.add(orderItem);
        }
    }

    public void chooseMaterial() {
// orderItem.setMaterial(selectedMaterial);
// selectedMaterial = null;
    }

    public void doSaveItem() {
        orderItem.setParent(stockOrder);
        if (newItemStatus) {
            stockOrder.addItem(orderItem);
        }
    }

    public void chooseOrg() {
        stockOrder.setOrg(selectedOrg);
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

    public void chooseOrgUnit() {
        stockOrder.setOrgUnit(selectedOrgUnit);
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

    public void checkUnitConv() {
        if (orderItem.getMaterial() == null) {
            uiLogger.error("你没有选择物料!");
            return;
        }

        if (orderItem.getMaterial().getUnitConv() == null) {
            uiLogger.error("你选择的物料没有可转换的单位!");
            return;
        }

        materialQuantity = null;
        materialPrice = null;
    }

    public void doUnitConv() {
        Double scale = orderItem.getMaterial().getUnitConv().getScale(materialUnitId);

        // 主单位数量=换算单位数量/换算率
        orderItem.setQuantity(//
                materialQuantity.divide(BigDecimal.valueOf(scale), QTY_ITEM_SCALE, RoundingMode.HALF_UP));
        // 主单位单价=换算单位数量*换算单位单价/主单位数量
        BigDecimal orderItemPrice = materialQuantity//
                .multiply(materialPrice)//
                .divide(orderItem.getQuantity(), MONEY_ITEM_SCALE, RoundingMode.HALF_UP);
        orderItem.getPrice().setValue(orderItemPrice);
    }

}
