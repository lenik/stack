package com.bee32.sem.purchase.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.sem.bom.dto.PartDto;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.bom.util.BomCriteria;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.util.ChanceCriteria;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.util.PeopleCriteria;
import com.bee32.sem.purchase.dto.MakeOrderDto;
import com.bee32.sem.purchase.dto.MakeOrderItemDto;
import com.bee32.sem.purchase.entity.MakeOrder;
import com.bee32.sem.world.monetary.CurrencyUtil;
import com.bee32.sem.world.monetary.MCValue;

@ForEntity(MakeOrder.class)
public class MakeOrderAdminBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private Date limitDateFrom;
    private Date limitDateTo;

    private boolean editable = false;

    private int goNumber;
    private int count;

    protected MakeOrderDto makeOrder = new MakeOrderDto().create();

    protected MakeOrderItemDto makeOrderItem = new MakeOrderItemDto().create();

    private BigDecimal makeOrderItemPrice = new BigDecimal(0);
    private Currency makeOrderItemPriceCurrency = CurrencyConfig.getNative();

    private String partPattern;
    private List<PartDto> parts;
    private PartDto selectedPart;

    private String customerPattern;
    private List<PartyDto> customers;
    private PartyDto selectedCustomer;

    private String chancePattern;
    private List<ChanceDto> chances;
    private ChanceDto selectedChance;

    private boolean newItemStatus = false;

    protected List<MakeOrderItemDto> itemsNeedToRemoveWhenModify = new ArrayList<MakeOrderItemDto>();

    public MakeOrderAdminBean() {
        Calendar c = Calendar.getInstance();
        // 取这个月的第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        limitDateFrom = c.getTime();

        // 最这个月的最后一天
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        limitDateTo = c.getTime();

        goNumber = 1;
        loadMakeOrder(goNumber);
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
        count = serviceFor(MakeOrder.class).count(//
                CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo));
        return count;
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

    public void setCount(int count) {
        this.count = count;
    }

    public String getPartPattern() {
        return partPattern;
    }

    public void setPartPattern(String partPattern) {
        this.partPattern = partPattern;
    }

    public List<PartDto> getParts() {
        return parts;
    }

    public void setParts(List<PartDto> parts) {
        this.parts = parts;
    }

    public PartDto getSelectedPart() {
        return selectedPart;
    }

    public void setSelectedPart(PartDto selectedPart) {
        this.selectedPart = selectedPart;
    }

    public String getCustomerPattern() {
        return customerPattern;
    }

    public void setCustomerPattern(String customerPattern) {
        this.customerPattern = customerPattern;
    }

    public List<PartyDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<PartyDto> customers) {
        this.customers = customers;
    }

    public PartyDto getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(PartyDto selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public String getChancePattern() {
        return chancePattern;
    }

    public void setChancePattern(String chancePattern) {
        this.chancePattern = chancePattern;
    }

    public List<ChanceDto> getChances() {
        return chances;
    }

    public void setChances(List<ChanceDto> chances) {
        this.chances = chances;
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







    public void limit() {
        loadMakeOrder(goNumber);
    }

    private void loadMakeOrder(int position) {
        //刷新总记录数
        getCount();

        goNumber = position;

        if(position < 1) {
            goNumber = 1;
            position = 1;
        }
        if(goNumber > count) {
            goNumber = count;
            position = count;
        }


        makeOrder = new MakeOrderDto().create();    //如果限定条件内没有找到makeOrder,则创建一个

        MakeOrder firstOrder = serviceFor(MakeOrder.class).getFirst( //
                new Offset(position - 1), //
                CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                Order.asc("id"));

        if (firstOrder != null)
            makeOrder = DTOs.marshal(MakeOrderDto.class, MakeOrderDto.ITEMS|MakeOrderDto.TASKS, firstOrder);

    }

    public void new_() {
        makeOrder = new MakeOrderDto().create();
        editable = true;
    }

    public void modify() {
        if(makeOrder.getId() == null) {
            uiLogger.warn("当前没有对应的单据");
            return;
        }

        itemsNeedToRemoveWhenModify.clear();

        editable = true;
    }

    public void save() {
        if(makeOrder.getId() == null) {
            //新增
            goNumber = count + 1;
        }

        try {
            MakeOrder _order = makeOrder.unmarshal();
            for(MakeOrderItemDto item : itemsNeedToRemoveWhenModify) {
                _order.removeItem(item.unmarshal());
            }

            serviceFor(MakeOrder.class).save(_order);
            uiLogger.info("保存成功");
            loadMakeOrder(goNumber);
            editable = false;
        } catch (Exception e) {
            uiLogger.warn("保存失败", e);
        }
    }

    public void cancel() {
        loadMakeOrder(goNumber);
        editable = false;
    }



    public void first() {
        goNumber = 1;
        loadMakeOrder(goNumber);
    }

    public void previous() {
        goNumber--;
        if (goNumber < 1)
            goNumber = 1;
        loadMakeOrder(goNumber);
    }

    public void go() {
        if (goNumber < 1) {
            goNumber = 1;
        } else if (goNumber > count) {
            goNumber = count;
        }
        loadMakeOrder(goNumber);
    }

    public void next() {
        goNumber++;

        if (goNumber > count)
            goNumber = count;
        loadMakeOrder(goNumber);
    }

    public void last() {
        goNumber = count + 1;
        loadMakeOrder(goNumber);
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
            loadMakeOrder(goNumber);
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
        if (partPattern != null && !partPattern.isEmpty()) {

            List<Part> _parts = serviceFor(Part.class).list(BomCriteria.findPartUseMaterialName(partPattern));

            parts = DTOs.mrefList(PartDto.class, _parts);
        }
    }

    public void choosePart() {
        makeOrderItem.setPart(selectedPart);

        selectedPart = null;
    }

    public void findCustomer() {
        if (customerPattern != null && !customerPattern.isEmpty()) {

            List<Party> _customers = serviceFor(Party.class).list( //
                    PeopleCriteria.customers(), //
                    new Or( //
                            new Like("name", "%" + customerPattern + "%"), //
                            new Like("fullName", "%" + customerPattern + "%")));

            customers = DTOs.mrefList(PartyDto.class, _customers);
        }
    }

    public void chooseCustomer() {
        makeOrder.setCustomer(selectedCustomer);
    }

    public void findChance() {
        List<Chance> _chances = serviceFor(Chance.class).list(//
                Order.desc("createdDate"), //
                ChanceCriteria.subjectLike(chancePattern));

        chances = DTOs.mrefList(ChanceDto.class, 0, _chances);
    }

    public void chooseChance() {
        makeOrder.setChance(selectedChance);
    }
}
