package com.bee32.sem.hr.dto;

import java.math.BigDecimal;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.dict.SimpleNameDictDto;
import com.bee32.sem.hr.entity.PersonEducationType;

public class PersonEducationTypeDto
        extends SimpleNameDictDto<PersonEducationType> {
    private static final long serialVersionUID = 1L;
    BigDecimal bonus;

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    @Override
    protected void _marshal(PersonEducationType source) {
        super._marshal(source);
        bonus = source.getBonus();
    }

    @Override
    protected void _unmarshalTo(PersonEducationType target) {
        super._unmarshalTo(target);
        target.setBonus(bonus);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        super._parse(map);
    }

}
