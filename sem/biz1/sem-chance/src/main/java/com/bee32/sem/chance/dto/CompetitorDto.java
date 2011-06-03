package com.bee32.sem.chance.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.color.GreenEntityDto;
import com.bee32.sem.chance.entity.Competitor;

public class CompetitorDto
        extends GreenEntityDto<Competitor, Long> {

    private static final long serialVersionUID = 1L;

    private String name;
    private ChanceDto chance;
    private double price;
    private String capability;
    private String solution;
    private String advantage;
    private String disvantage;
    private String tactic;
    private String remark;

    @Override
    protected void _marshal(Competitor source) {
        this.name = source.getName();
        this.chance = new ChanceDto(0, source.getChance());
        this.price = source.getPrice();
        this.capability = source.getCapability();
        this.solution = source.getSolution();
        this.advantage = source.getAdvantage();
        this.disvantage = source.getDisvantage();
        this.tactic = source.getTactic();
        this.remark = source.getRemark();
    }

    @Override
    protected void _unmarshalTo(Competitor target) {
        target.setName(name);
        merge(target, "chance", chance);
        target.setPrice(price);
        target.setCapability(capability);
        target.setSolution(solution);
        target.setAdvantage(advantage);
        target.setDisvantage(disvantage);
        target.setTactic(tactic);
        target.setRemark(remark);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChanceDto getChance() {
        return chance;
    }

    public void setChance(ChanceDto chance) {
        this.chance = chance;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCapability() {
        return capability;
    }

    public void setCapability(String capability) {
        this.capability = capability;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    public String getDisvantage() {
        return disvantage;
    }

    public void setDisvantage(String disvantage) {
        this.disvantage = disvantage;
    }

    public String getTactic() {
        return tactic;
    }

    public void setTactic(String tactic) {
        this.tactic = tactic;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
