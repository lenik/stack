package com.bee32.sem.account.entity;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 应收单应付单基类
 * @author jack
 *
 */
@Entity
@DiscriminatorValue("ABLE")
public class AccountAble extends CurrentAccount {

    private static final long serialVersionUID = 1L;

    Date expectedDate;

    /**
     * 预计收款或付款时间,用于账龄分析
     * @return
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Date expectedDate) {
        this.expectedDate = expectedDate;
    }


}
