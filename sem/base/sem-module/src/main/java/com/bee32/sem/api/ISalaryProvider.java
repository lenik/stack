package com.bee32.sem.api;

public interface ISalaryProvider {

    SalaryItem[] getSalaryItems(SalaryCalcContext ctx);

}
