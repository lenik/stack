package com.bee32.sem.makebiz.web;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.makebiz.dto.MakeOrderDto;
import com.bee32.sem.makebiz.dto.MakeOrderItemDto;
import com.bee32.sem.makebiz.entity.MakeOrder;
import com.bee32.sem.makebiz.service.MakebizService;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.people.dto.PartyDto;

@ForEntity(MakeOrder.class)
public class MakeOrderAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    public MakeOrderAdminBean() {
        super(MakeOrder.class, MakeOrderDto.class, MakeOrderDto.ITEM_ATTRIBUTES);
    }

    public void setChanceToApply(ChanceDto chance) {
        MakeOrderDto makeOrder = getOpenedObject();

        MakeOrder _checkSameChanceOrder = ctx.data.access(MakeOrder.class).getFirst(
                new Equals("chance.id", chance.getId()));
        if (_checkSameChanceOrder != null && !_checkSameChanceOrder.getId().equals(makeOrder.getId())) {
            uiLogger.error("此机会已经对应的订单!");
            return;
        }

        ctx.bean.getBean(MakebizService.class).chanceApplyToMakeOrder(chance, makeOrder);
    }

    public void exportToPdf() {
        MakeOrderDto makeOrder = this.getOpenedObject();
        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(makeOrder.getItems());
        String reportPath = "D:/work/secca/stack/sem/biz1/sem-makebiz/src/main/resources/resources/3/15/6/3/order/report1.jasper";
        try {
            Map<String, Object> reportParams = new HashMap<String, Object>();
            reportParams.put("id", makeOrder.getId());
            reportParams.put("createDate", makeOrder.getCreatedDate());
            reportParams.put("owner", makeOrder.getOwnerDisplayName());
            reportParams.put("label", makeOrder.getLabel());
            reportParams.put("customer", makeOrder.getCustomer().getDisplayName());
            reportParams.put("description", makeOrder.getDescription());

            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, reportParams, beanCollectionDataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/work/secca/stack/sem/biz1/sem-makebiz/src/main/resources/resources/3/15/6/3/order/report1.pdf");
        } catch (JRException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/
    PartyDto searchParty;

    public PartyDto getSearchParty() {
        return searchParty;
    }

    public void setSearchParty(PartyDto searchParty) {
        this.searchParty = searchParty;
    }

    public void addCustomerRestriction() {
        if (searchParty != null) {
            addSearchFragment("客户为 " + searchParty.getDisplayName(), //
                    new Equals("customer.id", searchParty.getId()));
            searchParty = null;
        }
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    final ListMBean<MakeOrderItemDto> itemsMBean = ListMBean.fromEL(this, //
            "openedObject.items", MakeOrderItemDto.class);

    public ListMBean<MakeOrderItemDto> getItemsMBean() {
        return itemsMBean;
    }

}
