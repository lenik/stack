package com.bee32.sem.hr.salary;

import java.math.BigDecimal;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.sem.api.AbstractSalaryVariableProvider;
import com.bee32.sem.hr.entity.PersonEducationType;

public class EducationTypeVariableProvider
        extends AbstractSalaryVariableProvider {

    @Override
    public BigDecimal evaluate(TextMap args, String variableName) {

        PersonEducationType first = DATA(PersonEducationType.class).getFirst(new Equals("label", variableName),
                Order.desc("beginTime"));
        if (first != null)
            return first.getBonus();
        return null;
    }

}
