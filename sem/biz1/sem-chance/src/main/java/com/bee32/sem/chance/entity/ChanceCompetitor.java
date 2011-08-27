package com.bee32.sem.chance.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.orm.ext.color.Green;
import com.bee32.plover.orm.ext.color.UIEntityAuto;
import com.bee32.plover.orm.ext.config.DecimalConfig;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;

/**
 * 竞争对手
 */
@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "chance_competitor_seq", allocationSize = 1)
public class ChanceCompetitor
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    private static final int CAPABILITY_LENGTH = 50;
    private static final int COMMON_LENGTH = 200;

    Chance chance;
    Party party;
    MCValue price = new MCValue();
    String capability;
    String solution;
    String advantage;
    String disvantage;
    String tactic;
    String comment;

    public ChanceCompetitor() {
    }

    /**
     * 对应销售机会
     */
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public Chance getChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        if (chance == null)
            throw new NullPointerException("chance");
        this.chance = chance;
    }

    /**
     * 公司名称
     */
    @Column(length = 50, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 竞争对手报价
     */
    @Embedded
    public MCValue getPrice() {
        return price;
    }

    public void setPrice(MCValue price) {
        if (price == null)
            throw new NullPointerException("price");
        this.price = price;
    }

    @Redundant
    @Column(precision = DecimalConfig.MONEY_ITEM_PRECISION, scale = DecimalConfig.MONEY_ITEM_SCALE)
    public BigDecimal getNativePrice()
            throws FxrQueryException {
        return price.getNativeValue(getCreatedDate());
    }

    void setNativePrice(BigDecimal nativePrice) {
    }

    /**
     * 对手的竞争能力
     */
    @Column(length = CAPABILITY_LENGTH, nullable = false)
    public String getCapability() {
        return capability;
    }

    public void setCapability(String capability) {
        if (capability == null)
            capability = "";
        this.capability = capability;
    }

    /**
     * 对手的解决方案
     */
    @Column(length = COMMON_LENGTH, nullable = false)
    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        if (solution == null)
            solution = "";
        this.solution = solution;
    }

    /**
     * 对手的优势
     */
    @Column(length = COMMON_LENGTH, nullable = false)
    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        if (advantage == null)
            advantage = "";
        this.advantage = advantage;
    }

    /**
     * 对手的劣势
     */
    @Column(length = COMMON_LENGTH, nullable = false)
    public String getDisvantage() {
        return disvantage;
    }

    public void setDisvantage(String disvantage) {
        if (disvantage == null)
            disvantage = "";
        this.disvantage = disvantage;
    }

    /**
     * 我们的应对策略
     */
    @Column(length = COMMON_LENGTH, nullable = false)
    public String getTactic() {
        return tactic;
    }

    public void setTactic(String tactic) {
        if (tactic == null)
            tactic = "";
        this.tactic = tactic;
    }

    /**
     * 备注
     */
    @Column(length = COMMON_LENGTH, nullable = false)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        if (comment == null)
            comment = "";
        this.comment = comment;
    }

}
