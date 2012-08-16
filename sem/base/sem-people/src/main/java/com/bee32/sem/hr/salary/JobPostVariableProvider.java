package com.bee32.sem.hr.salary;

import java.math.BigDecimal;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.api.AbstractSalaryVariableProvider;

public class JobPostVariableProvider
        extends AbstractSalaryVariableProvider {

    @Override
    public BigDecimal evaluate(TextMap args, String variableName) {
        return null;
    }

    // @Override
    // public SalaryElement[] getSalaryItems(SalaryCalcContext ctx) {
    // SalaryElement item = new SalaryElement();
    // item.setPath("base.post");
    // item.setLabel("职位补贴");
    // item.setValue(bonus);
    // return new SalaryElement[] { item };
    // }

}
