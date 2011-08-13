package com.bee32.sems.bom.entity;


import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;

import com.bee32.plover.orm.entity.EntityBean;

@Entity
public class MaterialPriceStrategy extends EntityBean<Long> {
    private Strategy strategy;
    private String memo;

    @Lob
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}
