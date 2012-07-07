package com.bee32.sem.chance.dto;

import java.math.BigDecimal;

import javax.free.ParseException;

import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.chance.entity.ChanceCompetitor;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.world.monetary.MCValue;

public class ChanceCompetitorDto
        extends UIEntityDto<ChanceCompetitor, Integer>
        implements IEnclosedObject<ChanceDto> {

    private static final long serialVersionUID = 1L;

    private ChanceDto chance;
    private PartyDto party;
    private BigDecimal price;
    private String capability;
    private String solution;
    private String advantage;
    private String disvantage;
    private String tactic;
    private String comment;

    @Override
    protected void _marshal(ChanceCompetitor source) {
        this.chance = mref(ChanceDto.class, source.getChance());
        this.party = mref(PartyDto.class, source.getParty());
        this.price = source.getPrice().toMutable().getValue();
        this.capability = source.getCapability();
        this.solution = source.getSolution();
        this.advantage = source.getAdvantage();
        this.disvantage = source.getDisvantage();
        this.tactic = source.getTactic();
        this.comment = source.getComment();
    }

    @Override
    protected void _unmarshalTo(ChanceCompetitor target) {
        merge(target, "chance", chance);
        merge(target, "party", party);
        target.setPrice(new MCValue(null, price));
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

    @Override
    public ChanceDto getEnclosingObject() {
        return getChance();
    }

    @Override
    public void setEnclosingObject(ChanceDto enclosingObject) {
        setChance(enclosingObject);
    }

    public ChanceDto getChance() {
        return chance;
    }

    public void setChance(ChanceDto chance) {
        if (chance == null)
            throw new NullPointerException("chance");
        this.chance = chance;
    }

    public PartyDto getParty() {
        return party;
    }

    public void setParty(PartyDto party) {
        if (party == null)
            throw new NullPointerException("party");
        this.party = party;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price == null)
            throw new NullPointerException("price");
        this.price = price;
    }

    @NLength(max = ChanceCompetitor.CAPABILITY_LENGTH)
    public String getCapability() {
        return capability;
    }

    public void setCapability(String capability) {
        this.capability = capability;
//        this.capability = TextUtil.normalizeSpace(capability);
    }

    @NLength(max = ChanceCompetitor.SOLUTION_LENGTH)
    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = TextUtil.normalizeSpace(solution);
    }

    @NLength(max = ChanceCompetitor.ADVANTAGE_LENGTH)
    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
//        this.advantage = TextUtil.normalizeSpace(advantage);
    }

    @NLength(max = ChanceCompetitor.DISVANTAGE_LENGTH)
    public String getDisvantage() {
        return disvantage;
    }

    public void setDisvantage(String disvantage) {
        this.disvantage = disvantage;
//        this.disvantage = TextUtil.normalizeSpace(disvantage);
    }

    @NLength(max = ChanceCompetitor.TACTIC_LENGTH)
    public String getTactic() {
        return tactic;
    }

    public void setTactic(String tactic) {
        this.tactic = TextUtil.normalizeSpace(tactic);
    }

    @NLength(max = ChanceCompetitor.COMMENT_LENGTH)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = TextUtil.normalizeSpace(comment);
    }

}
