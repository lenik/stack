package com.bee32.sem.hr.dto;

import java.math.BigDecimal;

import com.bee32.plover.ox1.dict.SimpleNameDictDto;
import com.bee32.sem.hr.entity.JobPerformance;

public class JobPerformanceDto
        extends SimpleNameDictDto<JobPerformance> {

    private static final long serialVersionUID = 1L;

    BigDecimal bonus;

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    @Override
    protected void _marshal(JobPerformance source) {
        super._marshal(source);
        this.bonus = source.getBonus();
    }

    @Override
    protected void _unmarshalTo(JobPerformance target) {
        super._unmarshalTo(target);
        target.setBonus(bonus);
    }

}
