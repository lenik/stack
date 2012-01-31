package com.bee32.sem.purchase.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.purchase.dto.MakeOrderDto;
import com.bee32.sem.purchase.entity.MakeOrder;

public class ChooseMakeOrderDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseMakeOrderDialogBean.class);

    String header = "选择生产订单";

    public ChooseMakeOrderDialogBean() {
        super(MakeOrder.class, MakeOrderDto.class, 0);
    }

    // Properties

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

}
