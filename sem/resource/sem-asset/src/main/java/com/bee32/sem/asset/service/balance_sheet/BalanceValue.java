package com.bee32.sem.asset.service.balance_sheet;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

@Service
public abstract class BalanceValue {
    Session session;
    BigDecimal value;
    Date date;
    boolean verified;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        setCalendar();
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void calc() {
        SQLQuery sqlQuery = session.createSQLQuery(buildQueryString());
        sqlQuery.setDate("date", getCalcDate());

        value = (BigDecimal) sqlQuery.uniqueResult();
        if(value == null) value = new BigDecimal(0);
        value = value.abs();
    }


    abstract void setCalendar();
    abstract Date getCalcDate();
    abstract String buildQueryString();

}
