package com.bee32.sem.asset.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.Entity;

public class AccountTicketSource implements Serializable {
    private static final long serialVersionUID = 1L;

    Serializable id;
    String type;
    String label;
    BigDecimal value;
    Class<? extends Entity<?>> classType;

    public Serializable getId() {
        return id;
    }

    public void setId(Serializable id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Class<? extends Entity<?>> getClassType() {
        return classType;
    }

    public void setClassType(Class<? extends Entity<?>> classType) {
        this.classType = (Class<? extends Entity<?>>) ClassUtil.skipProxies(classType);    //去掉hibernate生成的代理类
    }

}
