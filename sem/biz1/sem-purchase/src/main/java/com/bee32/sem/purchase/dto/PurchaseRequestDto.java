package com.bee32.sem.purchase.dto;

import java.io.Serializable;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.color.MomentIntervalDto;
import com.bee32.sem.purchase.entity.PurchaseRequest;

public class PurchaseRequestDto
        extends MomentIntervalDto<PurchaseRequest> {

    private static final long serialVersionUID = 1L;

    MaterialPlanDto plan;

    @Override
    protected void _marshal(PurchaseRequest source) {
        plan = mref(MaterialPlanDto.class, source.getPlan());
    }

    @Override
    protected void _unmarshalTo(PurchaseRequest target) {
        merge(target, "plan", plan);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public MaterialPlanDto getPlan() {
        return plan;
    }

    public void setPlan(MaterialPlanDto plan) {
        if (plan == null)
            throw new NullPointerException("plan");
        this.plan = plan;
    }

    @Override
    protected Serializable naturalId() {
        return naturalId(plan);
    }

}
