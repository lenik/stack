package com.bee32.sem.asset.service.profit_sheet;

import java.util.Calendar;
import java.util.Date;


public abstract class ThisYear extends ProfitValue {
    Calendar yearBegin;

    @Override
    void setCalendar() {
        queryDate = Calendar.getInstance();
        queryDate.setTime(date);
        queryDate.set(Calendar.HOUR_OF_DAY, 23);
        queryDate.set(Calendar.MINUTE, 59);
        queryDate.set(Calendar.SECOND, 59);
        queryDate.set(Calendar.MILLISECOND, 999);

        yearBegin = Calendar.getInstance();
        yearBegin.setTime(date);
        yearBegin.set(Calendar.MONTH, 0);   //0代表1月份
        yearBegin.set(Calendar.DAY_OF_MONTH, 1);
        yearBegin.set(Calendar.HOUR_OF_DAY, 0);
        yearBegin.set(Calendar.MINUTE, 0);
        yearBegin.set(Calendar.SECOND, 0);
        yearBegin.set(Calendar.MILLISECOND, 0);
    }

    @Override
    Date getBeginDate() {
        return yearBegin.getTime();
    }

    @Override
    Date getEndDate() {
        return queryDate.getTime();
    }
}
