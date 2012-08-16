package com.bee32.sem.salary.dto;

import java.math.BigDecimal;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.salary.entity.BaseBonus;

public class BaseBonusDto
        extends UIEntityDto<BaseBonus, Long> {

    private static final long serialVersionUID = 1L;

    boolean reward;
    BigDecimal bonus;

    @Override
    protected void _marshal(BaseBonus source) {
        super.marshal(source);
        bonus = source.getBonus();
    }

    @Override
    protected void _unmarshalTo(BaseBonus target) {
        super.unmarshalTo(target);
        target.setBonus(bonus);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        super.__parse(map);
    }

    public boolean isReward() {
        return reward;
    }

    public void setReward(boolean reward) {
        this.reward = reward;
    }

    public BigDecimal getBonus() {
        if (bonus == null)
            return new BigDecimal(0);
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    public String getFlagstring() {
        if (reward)
            return "奖";
        else
            return "罚";
    }

}
