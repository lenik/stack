package com.bee32.sem.hr.dto;

import java.math.BigDecimal;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.hr.entity.JobPost;

public class JobPostDto
        extends UIEntityDto<JobPost, Integer> {

    private static final long serialVersionUID = 1L;
    BigDecimal bonus;

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    @Override
    protected void _marshal(JobPost source) {
        label = source.getLabel();
        description = source.getDescription();
        bonus = source.getBonus();
    }

    @Override
    protected void _unmarshalTo(JobPost target) {
        target.setLabel(label);
        target.setDescription(description);
        target.setBonus(bonus);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
