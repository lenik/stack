package com.bee32.sem.asset.service.balance_sheet;

import java.util.Calendar;
import java.util.Date;


public abstract class LastYearBalance extends BalanceValue {
    Calendar lastYear;

    @Override
    void setCalendar() {
        lastYear = Calendar.getInstance();
        lastYear.setTime(date);
        lastYear.set(Calendar.MONTH, 0);
        lastYear.set(Calendar.DAY_OF_MONTH, 1);
        lastYear.add(Calendar.DAY_OF_MONTH, -1);
        lastYear.set(Calendar.HOUR_OF_DAY, 23);
        lastYear.set(Calendar.MINUTE, 59);
        lastYear.set(Calendar.SECOND, 59);
        lastYear.set(Calendar.MILLISECOND, 999);
    }

    @Override
    Date getCalcDate() {
        return lastYear.getTime();
    }
}
