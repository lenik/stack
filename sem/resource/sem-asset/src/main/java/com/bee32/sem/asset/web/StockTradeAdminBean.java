package com.bee32.sem.asset.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.free.IllegalUsageException;
import javax.free.UnexpectedException;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.asset.dto.AccountSubjectDto;
import com.bee32.sem.asset.dto.StockPurchaseDto;
import com.bee32.sem.asset.dto.StockSaleDto;
import com.bee32.sem.asset.dto.StockTradeDto;
import com.bee32.sem.asset.dto.StockTradeItemDto;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.asset.entity.StockPurchase;
import com.bee32.sem.asset.entity.StockSale;
import com.bee32.sem.asset.entity.StockTrade;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.world.monetary.CurrencyUtil;

public class StockTradeAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    protected StockTradeItemDto tradeItem = new StockTradeItemDto().create();

    private boolean newItemStatus = false;

    private MaterialDto selectedMaterial;
    private PartyDto selectedParty;

    protected List<StockTradeItemDto> itemsNeedToRemoveWhenModify = new ArrayList<StockTradeItemDto>();

    private String pageTitle;

    private AccountSubjectDto selectedAccountSubject;

    public StockTradeAdminBean() {
        super(StockTrade.class, StockTradeDto.class, 0);
        String type = getRequest().getParameter("type");
        if (type != null)
            switch (type) {
            case "SALE":
                pageTitle = "销售入账单管理";
                entityClass = StockSale.class;
                dtoClass = StockSaleDto.class;
                break;
            case "PURCHASE":
                pageTitle = "采购入账单管理";
                entityClass = StockPurchase.class;
                dtoClass = StockPurchaseDto.class;
                break;
            default:
                throw new IllegalUsageException("非正常方式进入入账单管理功能");
            }
    }

    public String getCreator() {
        StockTradeDto stockTrade = getActiveObject();
        if (stockTrade == null)
            return "";
        else
            return stockTrade.getOwnerDisplayName();
    }

    public List<StockTradeItemDto> getItems() {
        StockTradeDto stockTrade = getActiveObject();
        if (stockTrade == null)
            return null;
        return stockTrade.getItems();
    }

    public StockTradeItemDto getTradeItem() {
        return tradeItem;
    }

    public void setTradeItem(StockTradeItemDto tradeItem) {
        this.tradeItem = tradeItem;
    }

    public List<SelectItem> getCurrencies() {
        return CurrencyUtil.selectItems();
    }

    public MaterialDto getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(MaterialDto selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }

    public boolean isNewItemStatus() {
        return newItemStatus;
    }

    public void setNewItemStatus(boolean newItemStatus) {
        this.newItemStatus = newItemStatus;
    }

    public PartyDto getSelectedParty() {
        return selectedParty;
    }

    public void setSelectedParty(PartyDto selectedParty) {
        this.selectedParty = selectedParty;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void newItem() {
        StockTradeDto stockTrade = getActiveObject();
        tradeItem = new StockTradeItemDto().create();
        tradeItem.setTrade(stockTrade);

        newItemStatus = true;
    }

    public void modifyItem() {
        newItemStatus = false;
    }

    public void deleteItem() {
        StockTradeDto stockTrade = getActiveObject();
        stockTrade.removeItem(tradeItem);

        if (tradeItem.getId() != null) {
            itemsNeedToRemoveWhenModify.add(tradeItem);
        }
    }

    public void chooseMaterial() {
        tradeItem.setMaterial(selectedMaterial);

        selectedMaterial = null;
    }

    public void doSaveItem() {
        StockTradeDto stockTrade = getActiveObject();
        tradeItem.setTrade(stockTrade);
        if (newItemStatus) {
            stockTrade.addItem(tradeItem);
        }
    }

    public void chooseParty() {
        StockTradeDto stockTrade = getActiveObject();
        stockTrade.setParty(selectedParty);
    }

    private void loadStockTrade(int position) {
        StockTradeDto stockTrade = getActiveObject();
        // 刷新总记录数
        try {
// stockTrade = ((StockTradeDto) stockTradeDtoClass.newInstance()).create();
        } catch (Exception e) {
            throw new UnexpectedException(e);
        }

        StockTrade firstTrade = (StockTrade) serviceFor(entityClass).getFirst( //
                new Offset(position - 1), //
// CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                Order.asc("id"));

        if (firstTrade != null)
            stockTrade = (StockTradeDto) DTOs.marshal(dtoClass, StockTradeDto.ITEMS_FOR_UPDATE, firstTrade);

    }

    public void modify() {
        StockTradeDto stockTrade = getActiveObject();
        if (stockTrade.getId() == null) {
            uiLogger.warn("当前没有对应的单据");
            return;
        }

        itemsNeedToRemoveWhenModify.clear();
    }

    public void delete() {
        StockTradeDto stockTrade = getActiveObject();
        try {
            serviceFor(entityClass).delete(stockTrade.unmarshal());
            uiLogger.info("删除成功!");
// loadStockTrade(goNumber);
        } catch (Exception e) {
            uiLogger.warn("删除失败.", e);
        }

    }

    @Transactional
    public void save1() {
        StockTradeDto stockTrade = getActiveObject();
        if (stockTrade.getId() == null) {
            // 新增
// goNumber = count + 1;
        }

        try {

            for (StockTradeItemDto item : itemsNeedToRemoveWhenModify) {
                stockTrade.removeItem(item);
            }

            // 更新_trade中的金额
            BigDecimal total = new BigDecimal(0);
            for (StockTradeItemDto item : stockTrade.getItems()) {
                total = total.add(item.getNativeTotal());
            }

            stockTrade.getValue().setValue(total);

            StockTrade _trade = (StockTrade) stockTrade.unmarshal();

            serviceFor(entityClass).save(_trade);
            uiLogger.info("保存成功");
// loadStockTrade(goNumber);
        } catch (Exception e) {
            uiLogger.warn("保存失败", e);
        }
    }

    public AccountSubjectDto getSelectedAccountSubject() {
        return selectedAccountSubject;
    }

    public void setSelectedAccountSubject(AccountSubjectDto selectedAccountSubject) {
        this.selectedAccountSubject = selectedAccountSubject;
    }

    public List<AccountSubjectDto> getAccountSubjects() {
        StockTradeDto stockTrade = getActiveObject();
        // 在实体中,name代表科目代码，label代表科目名称
        List<AccountSubject> _subjects = serviceFor(AccountSubject.class).list(//
                new Like("id", "%" + stockTrade.getSubject().getName() + "%"));

        return DTOs.mrefList(AccountSubjectDto.class, 0, _subjects);
    }

    public void chooseAccountSubject() {
        StockTradeDto stockTrade = getActiveObject();
        stockTrade.setSubject(selectedAccountSubject);
    }

}
