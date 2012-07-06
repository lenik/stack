package com.bee32.sem.ar.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * 应收票据
 *
 */
@Entity
@DiscriminatorValue("NR")
public class NotesReceivable extends Received {
    private static final long serialVersionUID = 1L;

    String billNo;  //票据号
    BillType billType;    //票据类型

    /**
     * 承兑汇票号码
     * @return
     */
    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 承兑汇票类型
     * @return
     */
    @ManyToOne
    public BillType getBillType() {
        return billType;
    }

    public void setBillType(BillType billType) {
        this.billType = billType;
    }


}
