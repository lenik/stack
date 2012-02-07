package com.bee32.sem.purchase.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.entity.StockItemList;
import com.bee32.sem.purchase.entity.PlanOrder;

public class PlanOrderDto
        extends StockOrderDto {

    private static final long serialVersionUID = 1L;

    MaterialPlanDto plan;

    public PlanOrderDto() {
        super();
    }

    public PlanOrderDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(StockItemList _source) {
        super._marshal(_source);
        PlanOrder source = (PlanOrder) _source;
        plan = mref(MaterialPlanDto.class, source.getPlan());
    }

    @Override
    protected void _unmarshalTo(StockItemList _target) {
        super._unmarshalTo(_target);
        PlanOrder target = (PlanOrder) _target;
        merge(target, "plan", plan);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        super._parse(map);
    }

    public MaterialPlanDto getPlan() {
        return plan;
    }

    public void setPlan(MaterialPlanDto plan) {
        this.plan = plan;
    }
}
