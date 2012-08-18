package com.bee32.sem.salary.salary;

import java.math.BigDecimal;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.sem.api.AbstractSalaryVariableProvider;
import com.bee32.sem.salary.entity.BaseBonus;

public class BaseBonusVariableProvider
        extends AbstractSalaryVariableProvider {

    @Override
    public BigDecimal evaluate(TextMap args, String variableName) {
        BaseBonus first = DATA(BaseBonus.class).getFirst(//
                new Equals("label", variableName), //
                Order.desc("beginTime"));

        if (first == null)
            return null;

        return first.getBonus();
    }

}