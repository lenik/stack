package com.bee32.sem.asset.service.profit_sheet;

import java.util.Calendar;
import java.util.Date;

public abstract class ThisMonth extends ProfitValue {
    Calendar monthBegin;


    @Override
    void setCalendar() {
        queryDate = Calendar.getInstance();
        queryDate.setTime(date);
        queryDate.set(Calendar.HOUR_OF_DAY, 23);
        queryDate.set(Calendar.MINUTE, 59);
        queryDate.set(Calendar.SECOND, 59);
        queryDate.set(Calendar.MILLISECOND, 999);

        monthBegin = Calendar.getInstance();
        monthBegin.setTime(date);
        monthBegin.set(Calendar.DAY_OF_MONTH, 1);
        monthBegin.set(Calendar.HOUR_OF_DAY, 0);
        monthBegin.set(Calendar.MINUTE, 0);
        monthBegin.set(Calendar.SECOND, 0);
        monthBegin.set(Calendar.MILLISECOND, 0);
    }

    @Override
    Date getBeginDate() {
        return monthBegin.getTime();
    }

    @Override
    Date getEndDate() {
        return queryDate.getTime();
    }


}
