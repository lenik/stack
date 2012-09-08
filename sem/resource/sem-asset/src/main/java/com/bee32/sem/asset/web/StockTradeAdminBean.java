package com.bee32.sem.asset.web;

import java.math.BigDecimal;

import javax.free.IllegalUsageException;

import com.bee32.sem.asset.dto.StockPurchaseDto;
import com.bee32.sem.asset.dto.StockSaleDto;
import com.bee32.sem.asset.dto.StockTradeDto;
import com.bee32.sem.asset.dto.StockTradeItemDto;
import com.bee32.sem.asset.entity.AccountSubjects;
import com.bee32.sem.asset.entity.StockPurchase;
import com.bee32.sem.asset.entity.StockSale;
import com.bee32.sem.asset.entity.StockTrade;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;

public class StockTradeAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    String typeName;
    String subjectPrefix;

    public StockTradeAdminBean() {
        super(StockTrade.class, StockTradeDto.class, 0);
        String type = ctx.view.getRequest().getParameter("type");
        if (type != null)
            switch (type) {
            case "SALE":
                typeName = "销售入账单";
                setEntityType(StockSale.class);
                setEntityDtoType(StockSaleDto.class);
                subjectPrefix = BEAN(AccountSubjects.class).s1122.getId();
                break;
            case "PURCHASE":
                typeName = "采购入账单";
                setEntityType(StockPurchase.class);
                setEntityDtoType(StockPurchaseDto.class);
                subjectPrefix = BEAN(AccountSubjects.class).s2202.getId();
                break;
            default:
                throw new IllegalUsageException("非正常方式进入入账单管理功能");
            }
    }

    public String getTypeName() {
        return typeName;
    }

    public String getSubjectPrefix() {
        return subjectPrefix;
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    final ListMBean<StockTradeItemDto> itemsMBean = ListMBean.fromEL(this, //
            "openedObject.items", StockTradeItemDto.class);

    public ListMBean<StockTradeItemDto> getItemsMBean() {
        return itemsMBean;
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/
    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {

        for (StockTrade _stockTrade : uMap.<StockTrade> entitySet()) {
            StockTradeDto stockTrade = uMap.getSourceDto(_stockTrade);

            // 更新_trade中的金额
            BigDecimal total = new BigDecimal(0);
            for (StockTradeItemDto item : stockTrade.getItems()) {
                total = total.add(item.getNativeTotal());
            }

            stockTrade.getValue().setValue(total);
            _stockTrade.setValue(stockTrade.getValue().toImmutable());
        }

        return true;
    }

}
