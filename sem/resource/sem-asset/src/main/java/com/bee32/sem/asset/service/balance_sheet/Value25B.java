package com.bee32.sem.asset.service.balance_sheet;

import java.math.BigDecimal;

import org.hibernate.SQLQuery;


public class Value25B extends LastYearBalance {

    @Override
    String buildQueryString() {
        return Value25Assistant.buildQueryString(verified);
    }

    @Override
    public void calc() {
        BigDecimal value1;
        BigDecimal value2;

        SQLQuery sqlQuery1 = session.createSQLQuery(buildQueryString());
        sqlQuery1.setDate("date", getCalcDate());

        value1 = (BigDecimal) sqlQuery1.uniqueResult();
        if(value1 == null) value1 = new BigDecimal(0);

        SQLQuery sqlQuery2 = session.createSQLQuery(Value25Assistant.buildQueryString2(verified));
        sqlQuery2.setDate("date", getCalcDate());

        value2 = (BigDecimal) sqlQuery2.uniqueResult();
        if(value2 == null) value2 = new BigDecimal(0);

        value = value1.subtract(value2).abs();
    }
}
