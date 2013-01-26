package com.bee32.sem.makebiz.web;

import javax.faces.context.FacesContext;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.makebiz.dto.MakeOrderDto;
import com.bee32.sem.makebiz.dto.MakeTaskDto;
import com.bee32.sem.makebiz.entity.MakeOrder;
import com.bee32.sem.makebiz.entity.MakeTask;
import com.bee32.sem.misc.SimpleEntityViewBean;

@ForEntity(MakeTask.class)
public class MakeTaskListAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public MakeTaskListAdminBean() {
        super(MakeTask.class, MakeTaskDto.class, 0);
    }

    public void init() {
        String orderIdStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("orderId");
        if(orderIdStr == null) {
            clearSearchFragments();
        } else {
            MakeOrder order = ctx.data.getOrFail(MakeOrder.class, Long.parseLong(orderIdStr));
            searchOrder = DTOs.marshal(MakeOrderDto.class, order);
            addOrderRestriction();
        }

    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/



    /*************************************************************************
     * Section: Persistence
     *************************************************************************/


    /*************************************************************************
     * Section: Search
     *************************************************************************/
    @Override
    public void addNameOrLabelRestriction() {
        setSearchFragment("name", "名称含有 " + searchPattern,
                CommonCriteria.labelledWith(searchPattern, true));
        searchPattern = null;
    }

    MakeOrderDto searchOrder;

    public MakeOrderDto getSearchOrder() {
        return searchOrder;
    }

    public void setSearchOrder(MakeOrderDto searchOrder) {
        this.searchOrder = searchOrder;
    }

    public void addOrderRestriction() {
        if (searchOrder != null) {
            setSearchFragment("order", "定单为 " + searchOrder.getLabel(), //
                    new Equals("order.id", searchOrder.getId()));
            searchOrder = null;
        }
    }
}
