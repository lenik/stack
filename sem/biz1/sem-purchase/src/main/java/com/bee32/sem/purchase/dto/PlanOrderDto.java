package com.bee32.sem.purchase.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.dto.AbstractStockOrderDto;
import com.bee32.sem.purchase.entity.PlanOrder;

public class PlanOrderDto extends AbstractStockOrderDto<PlanOrder> {

    private static final long serialVersionUID = 1L;

    MaterialPlanDto plan;

    public PlanOrderDto() {
        super();
    }

    public PlanOrderDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(PlanOrder source) {
        plan = mref(MaterialPlanDto.class, source.getPlan());
        super._marshal(source);
    }

    @Override
    protected void _unmarshalTo(PlanOrder target) {
        merge(target, "plan", plan);
        super._unmarshalTo(target);
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        super._parse(map);
    }

    public MaterialPlanDto getPlan() {
        return plan;
    }

    public void setPlan(MaterialPlanDto plan) {
        this.plan = plan;
    }
}
