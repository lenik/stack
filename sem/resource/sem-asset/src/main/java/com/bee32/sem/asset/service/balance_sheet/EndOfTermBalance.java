package com.bee32.sem.asset.service.balance_sheet;

import java.util.Calendar;
import java.util.Date;

public abstract class EndOfTermBalance extends BalanceValue {
    Calendar endOfTerm;

    @Override
    void setCalendar() {
        endOfTerm = Calendar.getInstance();
        endOfTerm.setTime(date);
        endOfTerm.set(Calendar.HOUR_OF_DAY, 23);
        endOfTerm.set(Calendar.MINUTE, 59);
        endOfTerm.set(Calendar.SECOND, 59);
        endOfTerm.set(Calendar.MILLISECOND, 999);
    }

    @Override
    Date getCalcDate() {
        return endOfTerm.getTime();
    }
}
