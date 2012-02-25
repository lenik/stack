package com.bee32.sem.chance.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.sem.world.thing.AbstractItem;

/**
 * 报价
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "quotation_seq", allocationSize = 1)
public class Quotation
    extends AbstractItem {

    private static final long serialVersionUID = 1L;

    LectotypeItem lectotypeItem;

    BigDecimal discountRate = new BigDecimal(1);

    /**
     * AbstractItem中继承的quantity设为1，实际在报价中不使用
     */
    public Quotation() {
        setQuantity(new BigDecimal(1));
    }

    @ManyToOne
    public LectotypeItem getLectotypeItem() {
        return lectotypeItem;
    }

    public void setLectotypeItem(LectotypeItem lectotypeItem) {
        if(lectotypeItem == null)
            throw new NullPointerException("lectotypeItem");
        this.lectotypeItem = lectotypeItem;
    }


    /**
     * 折扣率，按中国人的折扣率习惯填写
     * @return
     */
    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    protected Date getFxrDate() {
        return this.getCreatedDate();
    }


}
