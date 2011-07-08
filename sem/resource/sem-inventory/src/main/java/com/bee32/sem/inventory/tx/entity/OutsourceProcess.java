package com.bee32.sem.inventory.tx.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.color.UIEntityAuto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;

/**
 * 外协加工作业。
 *
 * @see StockOrderSubject#F_CHECKOUT
 * @see StockOrderSubject#F_CHECKIN
 */
@Entity
public class OutsourceProcess
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    StockOrder sentOrder;
    StockOrder receivedOrder;

}
