package com.bee32.sem.chance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.plover.orm.ext.color.GreenEntity;

@Entity
public class Competitor
        extends GreenEntity<Long> {

    private static final long serialVersionUID = 1L;

    private String name;
    private Opportunity chance;
    private String price;
    private String capability;
    private String solution;
    private String advantage;
    private String disvantage;
    private String tactic;
    private String remark;

    public Competitor() {
    }

    /**
     * 公司名称
     */
    @Column(length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 对应销售机会
     */
    @ManyToOne
    public Opportunity getSalesChance() {
        return chance;
    }

    public void setSalesChance(Opportunity chance) {
        this.chance = chance;
    }

    /**
     * 报价 应该是什么类型?
     */
    @Column(length = 20)
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * 对手的竞争能力
     */
    @Column(length = 50)
    public String getCapability() {
        return capability;
    }

    public void setCapability(String competitiveness) {
        this.capability = competitiveness;
    }

    /**
     * 对手的解决方案
     */
    @Column(length = 200)
    public String getSolution() {
        return solution;
    }

    public void setSolution(String rivalProposal) {
        this.solution = rivalProposal;
    }

    /**
     * 对手的优势
     */
    @Column(length = 200)
    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    /**
     * 对手的劣势
     */
    @Column(length = 200)
    public String getDisvantage() {
        return disvantage;
    }

    public void setDisvantage(String inferiority) {
        this.disvantage = inferiority;
    }

    /**
     * 我们的应对策略
     */
    @Column(length = 200)
    public String getTactic() {
        return tactic;
    }

    public void setTactic(String tactic) {
        this.tactic = tactic;
    }

    /**
     * 备注
     */
    @Column(length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
