package com.bee32.sems.bom.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sems.bom.entity.MaterialPriceStrategy;

public class MaterialPriceStrategyDTO extends EntityDto<MaterialPriceStrategy, Long> {
    private String memo;
    private int strategy;

    public MaterialPriceStrategyDTO() {
        super();
    }

    public MaterialPriceStrategyDTO(MaterialPriceStrategy source) {
        super(source);
    }

    @Override
    protected void _marshal(MaterialPriceStrategy source) {
        memo = source.getMemo();
        strategy = source.getStrategy().value();
    }

    @Override
    protected void _unmarshalTo(MaterialPriceStrategy target) {
        throw new NotImplementedException();
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {

    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getStrategy() {
        return strategy;
    }

    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }
}
