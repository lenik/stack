package com.bee32.sem.chance.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.chance.entity.ChanceQuotation;
import com.bee32.sem.chance.entity.ChanceQuotationItem;
import com.bee32.sem.frame.ui.IEnclosedObject;
import com.bee32.sem.world.thing.AbstractOrderDto;

public class ChanceQuotationDto
        extends AbstractOrderDto<ChanceQuotation, ChanceQuotationItem, ChanceQuotationItemDto>
        implements IEnclosedObject<ChanceDto> {

    private static final long serialVersionUID = 1L;

    private ChanceDto chance;
    private String deliverInfo;
    private String payment;

    @Override
    protected void _marshal(ChanceQuotation source) {
        this.chance = mref(ChanceDto.class, source.getChance());
        this.deliverInfo = source.getDeliverInfo();
        this.payment = source.getPayment();
    }

    @Override
    protected void _unmarshalTo(ChanceQuotation target) {
        merge(target, "chance", chance);
        target.setDeliverInfo(deliverInfo);
        target.setPayment(payment);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
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

    @NLength(max = ChanceQuotation.DELIVER_INFO_LENGTH)
    public String getDeliverInfo() {
        return deliverInfo;
    }

    public void setDeliverInfo(String deliverInfo) {
        this.deliverInfo = TextUtil.normalizeSpace(deliverInfo);
    }

    @NLength(max = ChanceQuotation.PAYMENT_LENGTH)
    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = TextUtil.normalizeSpace(payment);
    }

}
