package com.bee32.sem.makebiz.web;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.makebiz.dto.MakeOrderDto;
import com.bee32.sem.makebiz.dto.MakeOrderItemDto;
import com.bee32.sem.makebiz.entity.MakeOrder;
import com.bee32.sem.makebiz.service.MakebizService;
import com.bee32.sem.misc.ScrollEntityViewBean;

@ForEntity(MakeOrder.class)
public class MakeOrderAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    public MakeOrderAdminBean() {
        super(MakeOrder.class, MakeOrderDto.class, 0);
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

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    final ListMBean<MakeOrderItemDto> itemsMBean = ListMBean.fromEL(this, //
            "openedObject.items", MakeOrderItemDto.class);

    public ListMBean<MakeOrderItemDto> getItemsMBean() {
        return itemsMBean;
    }

}
