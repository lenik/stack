package com.bee32.sem.pricing.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 报价公式基类
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("-")
@SequenceGenerator(name = "idgen", sequenceName = "pricing_formula_seq", allocationSize = 1)
public class PricingFormula extends UIEntityAuto<Long> {
    private static final long serialVersionUID = 1L;

    public static final int CONTENT_LENGTH = 200;

    String content;

    /**
     * 公式内容
     */
    @Column(length = CONTENT_LENGTH)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
