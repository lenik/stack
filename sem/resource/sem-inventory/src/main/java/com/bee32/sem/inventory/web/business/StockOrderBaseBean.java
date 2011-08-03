package com.bee32.sem.inventory.web.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.bee32.icsf.principal.User;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.orm.ext.tree.TreeCriteria;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.MaterialPreferredLocationDto;
import com.bee32.sem.inventory.dto.StockLocationDto;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.world.monetary.CurrencyConfig;
import com.bee32.sem.world.monetary.ICurrencyAware;
import com.bee32.sem.world.monetary.MCValue;

public class StockOrderBaseBean extends EntityViewBean implements
        ICurrencyAware {

    private static final long serialVersionUID = 1L;

    protected StockOrderSubject subject = null;

    protected StockWarehouseDto selectedWarehouse = new StockWarehouseDto().ref();

    protected boolean editable = false;

    protected StockOrderDto stockOrder = new StockOrderDto().ref();

    private StockOrderItemDto orderItem = new StockOrderItemDto().create().ref();
    private BigDecimal orderItemPrice = new BigDecimal(0);
    private String orderItemPriceCurrency = NATIVE_CURRENCY.getCurrencyCode();

    private boolean newItemStatus;

    private String materialPattern;
    private List<MaterialDto> materials;
    private MaterialDto selectedMaterial;

    private TreeNode locationRoot;
    private TreeNode selectedStockLocationNode;
    private StockLocationDto selectedPreferredStockLocation;


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
        List<StockWarehouse> stockWarehouses = serviceFor(StockWarehouse.class)
                .list();
        List<StockWarehouseDto> stockWarehouseDtos = DTOs.marshalList(
                StockWarehouseDto.class, stockWarehouses, true);

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

        User u = serviceFor(User.class).get(ownerId);
        return u.getDisplayName();
    }

    public List<StockOrderItemDto> getItems() {
        if (stockOrder != null) {
            return stockOrder.getItems();
        }
        return null;
    }


    public StockOrderItemDto getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(StockOrderItemDto orderItem) {
        this.orderItem = orderItem;
    }

    public List<SelectItem> getCurrencies() {
        List<Currency> currencies = CurrencyConfig.list();
        List<SelectItem> currencyItems = new ArrayList<SelectItem>();
        for (Currency c : currencies) {
            SelectItem i = new SelectItem();
            i.setLabel(CurrencyConfig.format(c));
            i.setValue(c.getCurrencyCode());
            currencyItems.add(i);
        }

        return currencyItems;
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
        if (orderItemPriceCurrency == null) {
            orderItemPriceCurrency = NATIVE_CURRENCY.getCurrencyCode();
        }
        return orderItemPriceCurrency;
    }

    public void setOrderItemPriceCurrency(String orderItemPriceCurrency) {
        this.orderItemPriceCurrency = orderItemPriceCurrency;
    }

    public TreeNode getLocationRoot() {
        if (locationRoot == null) {
            locationRoot = new DefaultTreeNode("", null);
        }
        return locationRoot;
    }

    public TreeNode getSelectedStockLocationNode() {
        return selectedStockLocationNode;
    }

    public void setSelectedStockLocationNode(TreeNode selectedStockLocationNode) {
        this.selectedStockLocationNode = selectedStockLocationNode;
    }

    public List<StockLocationDto> getPreferredLocations() {
        List<StockLocationDto> stockLocations = new ArrayList<StockLocationDto>();

        if (orderItem != null && orderItem.getMaterial() != null) {
            List<MaterialPreferredLocationDto> preferredLocations = orderItem.getMaterial()
                    .getPreferredLocations();
            if(preferredLocations != null) {
                for (MaterialPreferredLocationDto preferredLocation : preferredLocations) {
                    stockLocations.add(preferredLocation.getLocation());
                }
            }
        }

        return stockLocations;
    }

    public StockLocationDto getSelectedPreferredStockLocation() {
        return selectedPreferredStockLocation;
    }

    public void setSelectedPreferredStockLocation(
            StockLocationDto selectedPreferredStockLocation) {
        this.selectedPreferredStockLocation = selectedPreferredStockLocation;
    }

    public boolean isNewItemStatus() {
        return newItemStatus;
    }

    public void setNewItemStatus(boolean newItemStatus) {
        this.newItemStatus = newItemStatus;
    }








    protected void loadStockLocationTree() {
        if (selectedWarehouse != null) {
            locationRoot = new DefaultTreeNode(selectedWarehouse, null);

            List<StockLocation> topLocations = serviceFor(StockLocation.class)
                    .list(//
                    TreeCriteria.root(), //
                    new Equals("warehouse.id", selectedWarehouse.getId()));
            List<StockLocationDto> topLocationDtos = DTOs.marshalList(
                    StockLocationDto.class, -1, topLocations, true);

            for (StockLocationDto stockLocationDto : topLocationDtos) {
                loadStockLocationRecursive(stockLocationDto, locationRoot);
            }
        }
    }

    private void loadStockLocationRecursive(StockLocationDto stockLocationDto,
            TreeNode parentTreeNode) {
        TreeNode stockLocationNode = new DefaultTreeNode(stockLocationDto,
                parentTreeNode);

        List<StockLocationDto> subStockLocations = stockLocationDto
                .getChildren();
        for (StockLocationDto subStockLocation : subStockLocations) {
            loadStockLocationRecursive(subStockLocation, stockLocationNode);
        }
    }


    public void newItem() {
        orderItem = new StockOrderItemDto().create();
        orderItemPrice = new BigDecimal(0);
        orderItemPriceCurrency = NATIVE_CURRENCY.getCurrencyCode();

        newItemStatus = true;
    }

    public void modifyItem() {
        newItemStatus = false;
    }

    public void deleteItem() {
        stockOrder.removeItem(orderItem);

        if(orderItem.getId() != null) {
            itemsNeedToRemoveWhenModify.add(orderItem);
        }
    }


    public void findMaterial() {
        if (materialPattern != null && !materialPattern.isEmpty()) {

            List<Material> _materials = serviceFor(Material.class).list(
                    new Like("name", "%" + materialPattern + "%"));

            materials = DTOs.marshalList(MaterialDto.class, _materials);
        }
    }

    public void chooseMaterial() {
        orderItem.setMaterial(selectedMaterial);
    }

    public void doSaveItem() {
        orderItem.setParent(stockOrder);
        MCValue newPrice = new MCValue(
                Currency.getInstance(orderItemPriceCurrency), orderItemPrice);
        orderItem.setPrice(newPrice);
        if(newItemStatus) {
            stockOrder.addItem(orderItem);
        }
    }

    public void updateLocations() {

    }

    public void doSelectStockLocation() {
        orderItem.setLocation((StockLocationDto) selectedStockLocationNode.getData());
    }

    public void doSelectPreferredStockLocation() {
        orderItem.setLocation(selectedPreferredStockLocation);
    }
}
