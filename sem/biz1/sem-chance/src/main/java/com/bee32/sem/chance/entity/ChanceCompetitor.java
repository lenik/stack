package com.bee32.sem.chance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bee32.plover.orm.ext.color.Green;
import com.bee32.plover.orm.ext.color.UIEntityAuto;

/**
 * 竞争对手
 */
@Entity
@Green
public class ChanceCompetitor
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    String name;
    Chance chance;
    double price;
    String capability;
    String solution;
    String advantage;
    String disvantage;
    String tactic;
    String comment;

    public ChanceCompetitor() {
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
     * 竞争对手报价
     */
    @Column(length = 20, nullable = false)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * 对手的竞争能力
     */
    @Column(length = 50, nullable = false)
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
    @Column(length = 200, nullable = false)
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
    @Column(length = 200, nullable = false)
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
    @Column(length = 200, nullable = false)
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
    @Column(length = 200, nullable = false)
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
    @Column(length = 200, nullable = false)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        if (comment == null)
            comment = "";
        this.comment = comment;
    }

}
