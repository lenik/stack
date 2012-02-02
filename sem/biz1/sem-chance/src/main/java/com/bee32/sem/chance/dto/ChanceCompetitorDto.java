package com.bee32.sem.chance.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.chance.entity.ChanceCompetitor;
import com.bee32.sem.world.monetary.MutableMCValue;

public class ChanceCompetitorDto
        extends UIEntityDto<ChanceCompetitor, Integer> {

    private static final long serialVersionUID = 1L;

    private String name;
    private ChanceDto chance;
    private MutableMCValue price;
    private String capability;
    private String solution;
    private String advantage;
    private String disvantage;
    private String tactic;
    private String comment;

    @Override
    protected void _marshal(ChanceCompetitor source) {
        this.name = source.getName();
        this.chance = mref(ChanceDto.class, source.getChance());
        this.price = source.getPrice().toMutable();
        this.capability = source.getCapability();
        this.solution = source.getSolution();
        this.advantage = source.getAdvantage();
        this.disvantage = source.getDisvantage();
        this.tactic = source.getTactic();
        this.comment = source.getComment();
    }

    @Override
    protected void _unmarshalTo(ChanceCompetitor target) {
        target.setName(name);
        merge(target, "chance", chance);
        target.setPrice(price);
        target.setCapability(capability);
        target.setSolution(solution);
        target.setAdvantage(advantage);
        target.setDisvantage(disvantage);
        target.setTactic(tactic);
        target.setComment(comment);
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
        if (chance == null)
            throw new NullPointerException("chance");
        this.chance = chance;
    }

    public MutableMCValue getPrice() {
        return price;
    }

    public void setPrice(MutableMCValue price) {
        if (price == null)
            throw new NullPointerException("price");
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
