package com.bee32.sem.chance.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.bee32.plover.orm.ext.color.Green;
import com.bee32.plover.orm.ext.color.UIEntityAuto;

/**
 * 当前物料报价
 */

@Entity
@Green
public class CurrentPrice
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    // 对应的客户类型及不同类型客户的折扣 XXX
    List<Object> xxx;
    // 当前物料的基础价格
    @OneToOne
    BasePrice currentPrice;

    public CurrentPrice(List<Object> xxx, BasePrice currentPrice) {
        super();
        this.xxx = xxx;
        this.currentPrice = currentPrice;
    }

    public List<Object> getXxx() {
        return xxx;
    }

    public void setXxx(List<Object> xxx) {
        this.xxx = xxx;
    }

    public BasePrice getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BasePrice currentPrice) {
        this.currentPrice = currentPrice;
    }

    public static CurrentPrice pentax = new CurrentPrice(null, BasePrice.tempQD4);
    public static CurrentPrice pork = new CurrentPrice(null, BasePrice.tempQD9);
}
