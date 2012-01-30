package com.bee32.sem.purchase.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.sem.bom.dto.PartDto;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.purchase.dto.MakeOrderDto;
import com.bee32.sem.purchase.dto.MakeOrderItemDto;
import com.bee32.sem.purchase.entity.MakeOrder;
import com.bee32.sem.world.monetary.CurrencyUtil;
import com.bee32.sem.world.monetary.MCValue;

@ForEntity(MakeOrder.class)
public class MakeOrderAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    protected MakeOrderDto makeOrder = new MakeOrderDto().create();

    protected MakeOrderItemDto makeOrderItem = new MakeOrderItemDto().create();

    private BigDecimal makeOrderItemPrice = new BigDecimal(0);
    private Currency makeOrderItemPriceCurrency = CurrencyConfig.getNative();

    private PartDto selectedPart;
    private PartyDto selectedCustomer;
    private ChanceDto selectedChance;

    private boolean newItemStatus = false;

    protected List<MakeOrderItemDto> itemsNeedToRemoveWhenModify = new ArrayList<MakeOrderItemDto>();

    public MakeOrderAdminBean() {
        super(MakeOrder.class, MakeOrderDto.class, 0);
    }

    public MakeOrderDto getMakeOrder() {
        return makeOrder;
    }

    public void setMakeOrder(MakeOrderDto makeOrder) {
        this.makeOrder = makeOrder;
    }

    public String getCreator() {
        if (makeOrder == null)
            return "";
        else
            return makeOrder.getOwnerDisplayName();
    }

    public List<MakeOrderItemDto> getItems() {
        if (makeOrder == null)
            return null;
        return makeOrder.getItems();
    }

    public MakeOrderItemDto getMakeOrderItem() {
        return makeOrderItem;
    }

    public void setMakeOrderItem(MakeOrderItemDto makeOrderItem) {
        this.makeOrderItem = makeOrderItem;
    }

    public BigDecimal getMakeOrderItemPrice() {
        return makeOrderItemPrice;
    }

    public void setMakeOrderItemPrice(BigDecimal makeOrderItemPrice) {
        this.makeOrderItemPrice = makeOrderItemPrice;
    }

    public String getMakeOrderItemPriceCurrency() {
        if (makeOrderItemPriceCurrency == null)
            return CurrencyConfig.getNative().getCurrencyCode();
        else
            return makeOrderItemPriceCurrency.getCurrencyCode();
    }

    public void setMakeOrderItemPriceCurrency(String currencyCode) {
        this.makeOrderItemPriceCurrency = Currency.getInstance(currencyCode);
    }

    public List<SelectItem> getCurrencies() {
        return CurrencyUtil.selectItems();
    }
    public PartDto getSelectedPart() {
        return selectedPart;
    }

    public void setSelectedPart(PartDto selectedPart) {
        this.selectedPart = selectedPart;
    }

    public PartyDto getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(PartyDto selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public ChanceDto getSelectedChance() {
        return selectedChance;
    }

    public void setSelectedChance(ChanceDto selectedChance) {
        this.selectedChance = selectedChance;
    }

    public boolean isNewItemStatus() {
        return newItemStatus;
    }

    public void setNewItemStatus(boolean newItemStatus) {
        this.newItemStatus = newItemStatus;
    }

    private void loadMakeOrder(int position) {
        // 刷新总记录数

        makeOrder = new MakeOrderDto().create(); // 如果限定条件内没有找到makeOrder,则创建一个

        MakeOrder firstOrder = serviceFor(MakeOrder.class).getFirst( //
                new Offset(position - 1), //
//                CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                Order.asc("id"));

        if (firstOrder != null)
            makeOrder = DTOs.marshal(MakeOrderDto.class, MakeOrderDto.ITEMS | MakeOrderDto.TASKS, firstOrder);

    }

    public void modify() {
        if (makeOrder.getId() == null) {
            uiLogger.warn("当前没有对应的单据");
            return;
        }
        itemsNeedToRemoveWhenModify.clear();
    }

    public void save1() {
        if (makeOrder.getId() == null) {
            // 新增
//            goNumber = count + 1;
        }

        try {
            MakeOrder _order = makeOrder.unmarshal();
            for (MakeOrderItemDto item : itemsNeedToRemoveWhenModify) {
                _order.removeItem(item.unmarshal());
            }

            serviceFor(MakeOrder.class).save(_order);
            uiLogger.info("保存成功");
//            loadMakeOrder(goNumber);
        } catch (Exception e) {
            uiLogger.warn("保存失败", e);
        }
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
//            loadMakeOrder(goNumber);
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
//                    BomCriteria.findPartUseMaterialName(partPattern));
    }

    public void choosePart() {
        makeOrderItem.setPart(selectedPart);

        selectedPart = null;
    }

    public void findCustomer() {
                    // PeopleCriteria.customers(), //
    }

    public void chooseCustomer() {
        makeOrder.setCustomer(selectedCustomer);
    }

    public void chooseChance() {
        makeOrder.setChance(selectedChance);
    }

    @Override
    public List<?> getSelection() {
        return listOfNonNulls(makeOrder);
    }

}
