package com.bee32.sem.chance.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.bee32.plover.orm.ext.color.Blue;
import com.bee32.plover.orm.ext.color.UIEntityAuto;
import com.bee32.sem.inventory.entity.MaterialPrice;

/**
 * 当前物料报价
 */
@Entity
@Blue
public class CurrentPrice
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    // 对应的客户类型及不同类型客户的折扣 XXX
    List<Object> xxx;
    // 当前物料的基础价格
    @OneToOne
    MaterialPrice currentPrice;

}
