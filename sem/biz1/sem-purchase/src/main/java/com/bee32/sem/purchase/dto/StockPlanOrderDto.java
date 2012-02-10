package com.bee32.sem.purchase.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.entity.AbstractStockItemList;
import com.bee32.sem.purchase.entity.StockPlanOrder;

public class StockPlanOrderDto
        extends StockOrderDto {

    private static final long serialVersionUID = 1L;

    MaterialPlanDto plan;

    public StockPlanOrderDto() {
        super();
    }

    public StockPlanOrderDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(AbstractStockItemList<?> _source) {
        super._marshal(_source);
        StockPlanOrder source = (StockPlanOrder) _source;
        plan = mref(MaterialPlanDto.class, source.getPlan());
    }

    @Override
    protected void _unmarshalTo(AbstractStockItemList<?> _target) {
        super._unmarshalTo(_target);
        StockPlanOrder target = (StockPlanOrder) _target;
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
