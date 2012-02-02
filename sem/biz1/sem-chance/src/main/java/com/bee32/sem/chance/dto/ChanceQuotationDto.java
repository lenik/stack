package com.bee32.sem.chance.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.chance.entity.ChanceQuotation;
import com.bee32.sem.chance.entity.ChanceQuotationItem;
import com.bee32.sem.world.thing.AbstractOrderDto;

public class ChanceQuotationDto
        extends AbstractOrderDto<ChanceQuotation, ChanceQuotationItem, ChanceQuotationItemDto> {

    private static final long serialVersionUID = 1L;

    private ChanceDto chance;
    private String recommend;
    private String payment;

    @Override
    protected void _marshal(ChanceQuotation source) {
        this.chance = mref(ChanceDto.class, source.getChance());
        this.recommend = source.getRecommend();
        this.payment = source.getPayment();
    }

    @Override
    protected void _unmarshalTo(ChanceQuotation target) {
        merge(target, "chance", chance);
        target.setRecommend(recommend);
        target.setPayment(payment);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public ChanceDto getChance() {
        return chance;
    }

    public void setChance(ChanceDto chance) {
        if (chance == null)
            throw new NullPointerException("chance");
        this.chance = chance;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

}
