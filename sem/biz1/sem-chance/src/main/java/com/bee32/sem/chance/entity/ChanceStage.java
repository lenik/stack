package com.bee32.sem.chance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.ShortNameDict;

/**
 * 机会阶段推进
 */
@Entity
public class ChanceStage
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    int order;

    public ChanceStage() {
        super();
    }

    public ChanceStage(int order, String name, String description) {
        super(name, description);
        this.order = order;
    }

    /**
     * 机会推进顺序
     */
    @Column(nullable = false)
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public static ChanceStage INITIAL = new ChanceStage(1, "INIT", "初步沟通");

    public static ChanceStage MEAT = new ChanceStage(2, "MEAT", "实质进展");

    public static ChanceStage QUOTATION = new ChanceStage(3, "QUOT", "已经报价");

    public static ChanceStage PAYMENT = new ChanceStage(4, "PAYM", "合同付款洽谈");

    public static ChanceStage CONTRACT = new ChanceStage(5, "CONT", "合同签订");
}
