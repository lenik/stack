package com.bee32.sem.purchase.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.purchase.dto.MakeOrderDto;
import com.bee32.sem.purchase.dto.MakeOrderItemDto;
import com.bee32.sem.purchase.entity.MakeOrder;

@ForEntity(MakeOrder.class)
public class MakeOrderAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    ListMBean<MakeOrderItemDto> itemsMBean = ListMBean.fromEL(this, "openedObject.items", MakeOrderItemDto.class);

    public MakeOrderAdminBean() {
        super(MakeOrder.class, MakeOrderDto.class, 0);
    }

    public ListMBean<MakeOrderItemDto> getItemsMBean() {
        return itemsMBean;
    }

}
