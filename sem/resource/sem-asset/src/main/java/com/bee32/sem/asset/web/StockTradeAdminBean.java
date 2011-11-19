package com.bee32.sem.asset.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.free.UnexpectedException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.sem.asset.dto.StockTradeDto;
import com.bee32.sem.asset.dto.StockTradeItemDto;
import com.bee32.sem.asset.entity.StockPurchase;
import com.bee32.sem.asset.entity.StockSale;
import com.bee32.sem.asset.entity.StockTrade;
import com.bee32.sem.asset.entity.StockTradeItem;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.misc.EntityCriteria;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.world.monetary.CurrencyUtil;
import com.bee32.sem.world.monetary.MCValue;

public class StockTradeAdminBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private Class stockTradeClass;
    private Class stockTradeDtoClass;

    protected boolean editable = false;

    protected StockTradeDto stockTrade;

    protected StockTradeItemDto tradeItem = new StockTradeItemDto().create();

    private boolean newItemStatus = false;

    private String materialPattern;
    private List<MaterialDto> materials;
    private MaterialDto selectedMaterial;

    private String orgPattern;
    private List<OrgDto> orgs;
    private OrgDto selectedOrg;

    protected List<StockTradeItemDto> itemsNeedToRemoveWhenModify = new ArrayList<StockTradeItemDto>();

    private String pageTitle;

    private Date limitDateFrom;
    private Date limitDateTo;


    private int goNumber;
    private int count;

    public StockTradeAdminBean() {
        Calendar c = Calendar.getInstance();
        // 取这个月的第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        limitDateFrom = c.getTime();

        // 最这个月的最后一天
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        limitDateTo = c.getTime();

        goNumber = 1;


        try {
            HttpServletRequest req = (HttpServletRequest) FacesContext
                    .getCurrentInstance().getExternalContext().getRequest();
            String s = req.getParameter("type").toString();
            if (s.equals("SALE")) {
                pageTitle = "销售入账单管理";
                stockTradeClass = StockSale.class;
            } else {
                if (s.equals("PURCHASE")) {
                    pageTitle = "采购入账单管理";
                    stockTradeClass = StockPurchase.class;
                } else {
                    throw new Exception("非正常方式进入入账单管理功能");
                }
            }
        } catch (Exception e) {
            uiLogger.warn("非正常方式进入库存业务功能!", e);
        }

        stockTradeDtoClass = EntityUtil.getDtoType(stockTradeClass);

        try {
            stockTrade = ((StockTradeDto)stockTradeDtoClass.newInstance()).create();
        } catch (Exception e) {
            throw new UnexpectedException(e);
        }

        loadStockTrade(goNumber);
    }



    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public StockTradeDto getStockTrade() {
        return stockTrade;
    }

    public void setStockTrade(StockTradeDto stockTrade) {
        this.stockTrade = stockTrade;
    }

    public String getCreator() {
        if (stockTrade == null)
            return "";
        else
            return stockTrade.getOwnerDisplayName();
    }

    public List<StockTradeItemDto> getItems() {
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

    public String getPageTitle() {
        return pageTitle;
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

    public int getGoNumber() {
        return goNumber;
    }

    public void setGoNumber(int goNumber) {
        this.goNumber = goNumber;
    }

    public int getCount() {
        count = serviceFor(stockTradeClass).count(//
                EntityCriteria.createdBetweenEx(limitDateFrom, limitDateTo));
        return count;
    }








    public void newItem() {
        tradeItem = new StockTradeItemDto().create();
        tradeItem.setTrade(stockTrade);

        newItemStatus = true;
    }

    public void modifyItem() {
        newItemStatus = false;
    }

    public void deleteItem() {
        stockTrade.removeItem(tradeItem);

        if (tradeItem.getId() != null) {
            itemsNeedToRemoveWhenModify.add(tradeItem);
        }
    }

    public void findMaterial() {
        if (materialPattern != null && !materialPattern.isEmpty()) {

            List<Material> _materials = serviceFor(Material.class).list(new Like("label", "%" + materialPattern + "%"));

            materials = DTOs.mrefList(MaterialDto.class, _materials);
        }
    }

    public void chooseMaterial() {
        tradeItem.setMaterial(selectedMaterial);

        selectedMaterial = null;
    }

    public void doSaveItem() {
        tradeItem.setTrade(stockTrade);
        if (newItemStatus) {
            stockTrade.addItem(tradeItem);
        }
    }

    public void findOrg() {
        if (orgPattern != null && !orgPattern.isEmpty()) {

            List<Org> _orgs = serviceFor(Org.class).list( //
                    new Or(new Like("name", "%" + orgPattern + "%"),
                            new Like("fullName", "%" + orgPattern + "%")));

            orgs = DTOs.marshalList(OrgDto.class, _orgs);
        }
    }

    public void chooseOrg() {
        stockTrade.setParty(selectedOrg);
    }

    private void loadStockTrade(int position) {
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


        try {
            stockTrade = ((StockTradeDto)stockTradeDtoClass.newInstance()).create();
        } catch (Exception e) {
            throw new UnexpectedException(e);
        }

        StockTrade firstTrade = (StockTrade) serviceFor(stockTradeClass).getFirst( //
                new Offset(position - 1), //
                EntityCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                Order.asc("id"));

        if (firstTrade != null)
            stockTrade = DTOs.marshal(stockTradeDtoClass, firstTrade);

    }

    public void limit() {
        loadStockTrade(goNumber);
    }

    public void new_() {
        try {
            stockTrade = ((StockTradeDto)stockTradeDtoClass.newInstance()).create();
        } catch (Exception e) {
            throw new UnexpectedException(e);
        }

        editable = true;
    }

    public void modify() {
        if(stockTrade.getId() == null) {
            uiLogger.warn("当前没有对应的单据");
            return;
        }

        itemsNeedToRemoveWhenModify.clear();

        editable = true;
    }

    public void delete() {
        try {
            serviceFor(stockTradeClass).delete(stockTrade.unmarshal());
            uiLogger.info("删除成功!");
            loadStockTrade(goNumber);
        } catch (Exception e) {
            uiLogger.warn("删除失败.", e);
        }


    }

    @Transactional
    public void save() {
        if(stockTrade.getId() == null) {
            //新增
            goNumber = count + 1;
        }

        try {
            StockTrade _trade = (StockTrade)stockTrade.unmarshal();
            for(StockTradeItemDto item : itemsNeedToRemoveWhenModify) {
                _trade.removeItem(item.unmarshal());
            }

            //更新_trade中的金额
            BigDecimal total = new BigDecimal(0);
            for(StockTradeItem item : _trade.getItems()) {
                total = total.add(item.getNativeTotal());
            }

            _trade.setValue(new MCValue(CurrencyConfig.getNative(), total));

            serviceFor(stockTradeClass).save(_trade);
            uiLogger.info("保存成功");
            loadStockTrade(goNumber);
            editable = false;
        } catch (Exception e) {
            uiLogger.warn("保存失败", e);
        }
    }

    public void cancel() {

        loadStockTrade(goNumber);
        editable = false;
    }

    public void first() {
        goNumber = 1;
        loadStockTrade(goNumber);
    }

    public void previous() {
        goNumber--;
        if (goNumber < 1)
            goNumber = 1;
        loadStockTrade(goNumber);
    }

    public void go() {
        if (goNumber < 1) {
            goNumber = 1;
        } else if (goNumber > count) {
            goNumber = count;
        }
        loadStockTrade(goNumber);
    }

    public void next() {
        goNumber++;

        if (goNumber > count)
            goNumber = count;
        loadStockTrade(goNumber);
    }

    public void last() {
        goNumber = count + 1;
        loadStockTrade(goNumber);
    }
}
