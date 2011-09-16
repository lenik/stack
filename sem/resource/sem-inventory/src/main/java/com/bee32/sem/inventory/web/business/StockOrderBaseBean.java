package com.bee32.sem.inventory.web.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.context.annotation.Scope;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockItemList;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.inventory.service.IStockQuery;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.dto.OrgUnitDto;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.people.entity.OrgUnit;
import com.bee32.sem.people.util.PeopleCriteria;
import com.bee32.sem.world.monetary.CurrencyUtil;
import com.bee32.sem.world.monetary.MCValue;

@Scope("view")
public abstract class StockOrderBaseBean
        extends LocationAboutBean {

    private static final long serialVersionUID = 1L;

    protected StockOrderSubject subject = null;

    protected StockWarehouseDto selectedWarehouse = new StockWarehouseDto().ref();

    protected boolean editable = false;

    protected StockOrderDto stockOrder = new StockOrderDto().create().ref();

    protected StockOrderItemDto orderItem = new StockOrderItemDto().create().ref();
    private BigDecimal orderItemPrice = new BigDecimal(0);
    private Currency orderItemPriceCurrency = CurrencyConfig.getNative();

    private boolean newItemStatus = false;

    private String materialPattern;
    private List<MaterialDto> materials;
    private MaterialDto selectedMaterial;

    private String orgPattern;
    private List<OrgDto> orgs;
    private OrgDto selectedOrg;

    private String orgUnitPattern;
    private List<OrgUnitDto> orgUnits;
    private OrgUnitDto selectedOrgUnit;

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

    public String getOrgPattern() {
        return orgPattern;
    }

    public void setOrgPattern(String orgPattern) {
        this.orgPattern = orgPattern;
    }

    public List<OrgDto> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<OrgDto> orgs) {
        this.orgs = orgs;
    }

    public OrgDto getSelectedOrg() {
        return selectedOrg;
    }

    public void setSelectedOrg(OrgDto selectedOrg) {
        this.selectedOrg = selectedOrg;
    }

    public String getOrgUnitPattern() {
        return orgUnitPattern;
    }

    public void setOrgUnitPattern(String orgUnitPattern) {
        this.orgUnitPattern = orgUnitPattern;
    }

    public List<OrgUnitDto> getOrgUnits() {
        return orgUnits;
    }

    public void setOrgUnits(List<OrgUnitDto> orgUnits) {
        this.orgUnits = orgUnits;
    }

    public OrgUnitDto getSelectedOrgUnit() {
        return selectedOrgUnit;
    }

    public void setSelectedOrgUnit(OrgUnitDto selectedOrgUnit) {
        this.selectedOrgUnit = selectedOrgUnit;
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

            List<Material> _materials = serviceFor(Material.class).list(new Like("label", "%" + materialPattern + "%"));

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

    public void findOrg() {
        if (orgPattern != null && !orgPattern.isEmpty()) {

            List<Org> _orgs = serviceFor(Org.class).list(
                    new Or(new Like("name", "%" + orgPattern + "%"), new Like("fullName", "%" + orgPattern + "%")));

            orgs = DTOs.marshalList(OrgDto.class, _orgs);
        }
    }

    public void chooseOrg() {
        stockOrder.setOrg(selectedOrg);
    }

    public void findOrgUnit() {
        if (orgUnitPattern != null && !orgUnitPattern.isEmpty()) {

            List<OrgUnit> _orgUnits = null;

            if (stockOrder.getOrg().getId() != null) {
                // 如果前面选中了某个公司，则查找该公司中的部门
                _orgUnits = serviceFor(OrgUnit.class).list(new Like("name", "%" + orgUnitPattern + "%"),
                        new Equals("org.id", selectedOrg.getId()));
            } else {
                // 如果没有选择公司，则表示查找tag为内部的公司中的部门(即为本公司内部的部门)
                _orgUnits = serviceFor(OrgUnit.class).list(PeopleCriteria.internalOrgUnitWithName(orgUnitPattern));
            }

            orgUnits = DTOs.marshalList(OrgUnitDto.class, _orgUnits);
        }
    }

    public void chooseOrgUnit() {
        stockOrder.setOrgUnit(selectedOrgUnit);
    }


    public List<StockOrderItemDto> getStockQueryItems() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);

        StockWarehouse w = selectedWarehouse.unmarshal();
        Material m = orderItem.getMaterial().unmarshal();

        StockQueryOptions opts = new StockQueryOptions(c.getTime());
        opts.setWarehouse(w);
        opts.setCbatch(null, false);
        opts.setLocation(null, false);

        IStockQuery q = getBean(IStockQuery.class);
        StockItemList list = q.getActualQuantity(m, opts);
        return DTOs.marshalList(StockOrderItemDto.class, list.getItems());
    }
}
