package com.bee32.sem.inventory.web.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.principal.User;
import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.misc.i18n.CurrencyConfig;
import com.bee32.sem.world.monetary.CurrencyUtil;
import com.bee32.sem.world.monetary.MCValue;

@Component
@Scope("view")
public abstract class StockOrderBaseBean extends LocationAboutBean {

    private static final long serialVersionUID = 1L;

    protected StockOrderSubject subject = null;

    protected StockWarehouseDto selectedWarehouse = new StockWarehouseDto().ref();

    protected boolean editable = false;

    protected StockOrderDto stockOrder = new StockOrderDto().ref();

    protected StockOrderItemDto orderItem = new StockOrderItemDto().create().ref();
    private BigDecimal orderItemPrice = new BigDecimal(0);
    private Currency orderItemPriceCurrency = CurrencyConfig.getNative();

    private boolean newItemStatus;

    private String materialPattern;
    private List<MaterialDto> materials;
    private MaterialDto selectedMaterial;


    protected List<StockOrderItemDto> itemsNeedToRemoveWhenModify = new ArrayList<StockOrderItemDto>();

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
        List<StockWarehouseDto> stockWarehouseDtos = DTOs.marshalList(StockWarehouseDto.class, stockWarehouses, true);

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

    public String getCreator() {
        if (stockOrder == null)
            return "";

        Integer ownerId = stockOrder.getOwnerId();
        if (ownerId == null)
            return "";

        User u = serviceFor(User.class).getOrFail(ownerId);
        return u.getDisplayName();
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

    public String getMaterialPattern() {
        return materialPattern;
    }

    public void setMaterialPattern(String materialPattern) {
        this.materialPattern = materialPattern;
    }

    public List<MaterialDto> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialDto> materials) {
        this.materials = materials;
    }

    public MaterialDto getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(MaterialDto selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }

    public BigDecimal getOrderItemPrice() {
        return orderItemPrice;
    }

    public void setOrderItemPrice(BigDecimal orderItemPrice) {
        this.orderItemPrice = orderItemPrice;
    }

    public String getOrderItemPriceCurrency() {
        if (orderItemPriceCurrency == null)
            return CurrencyConfig.getNative().getCurrencyCode();
        else
            return orderItemPriceCurrency.getCurrencyCode();
    }

    public void setOrderItemPriceCurrency(String currencyCode) {
        this.orderItemPriceCurrency = Currency.getInstance(currencyCode);
    }



    public boolean isNewItemStatus() {
        return newItemStatus;
    }

    public void setNewItemStatus(boolean newItemStatus) {
        this.newItemStatus = newItemStatus;
    }



    public void newItem() {
        orderItem = new StockOrderItemDto().create();
        orderItemPrice = new BigDecimal(0);
        orderItemPriceCurrency = CurrencyConfig.getNative();

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

    public void findMaterial() {
        if (materialPattern != null && !materialPattern.isEmpty()) {

            List<Material> _materials = serviceFor(Material.class).list(new Like("name", "%" + materialPattern + "%"));

            materials = DTOs.marshalList(MaterialDto.class, _materials);
        }
    }

    public void chooseMaterial() {
        orderItem.setMaterial(selectedMaterial);
    }

    public void doSaveItem() {
        orderItem.setParent(stockOrder);
        MCValue newPrice = new MCValue(orderItemPriceCurrency, orderItemPrice);
        orderItem.setPrice(newPrice);
        if (newItemStatus) {
            stockOrder.addItem(orderItem);
        }
    }



}
