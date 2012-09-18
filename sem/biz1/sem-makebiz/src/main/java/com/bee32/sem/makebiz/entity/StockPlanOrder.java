package com.bee32.sem.makebiz.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;

/**
 * 物料锁定出库单
 *
 * 物料计划中，在考虑仓库物料的当前库存量和安全库存量的前提下，对可用的物料进行出库。这里即为对应的计划出库单。
 *
 * @author jack
 *
 */
@Entity
@DiscriminatorValue("PLA")
public class StockPlanOrder
        extends StockOrder {

    private static final long serialVersionUID = 1L;

    public StockPlanOrder() {
        this.setSubject(StockOrderSubject.PLAN_OUT);
    }

}
