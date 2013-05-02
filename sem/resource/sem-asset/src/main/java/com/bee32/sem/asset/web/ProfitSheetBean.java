package com.bee32.sem.asset.web;

import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.asset.service.balance_sheet.Value1A;
import com.bee32.sem.asset.service.balance_sheet.Value1B;
import com.bee32.sem.asset.service.balance_sheet.Value2A;
import com.bee32.sem.asset.service.balance_sheet.Value2B;
import com.bee32.sem.asset.service.balance_sheet.Value3A;
import com.bee32.sem.asset.service.balance_sheet.Value3B;

public class ProfitSheetBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    @Inject
    SessionFactory sessionFactory;

    Date queryDate = new Date();

    boolean verified;

    BigDecimal v1A;
    BigDecimal v1B;

    BigDecimal v2A;
    BigDecimal v2B;

    BigDecimal v3A;
    BigDecimal v3B;


    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public BigDecimal getV1A() {
        return v1A;
    }

    public BigDecimal getV1B() {
        return v1B;
    }

    public BigDecimal getV2A() {
        return v2A;
    }

    public BigDecimal getV2B() {
        return v2B;
    }

    public BigDecimal getV3A() {
        return v3A;
    }

    public BigDecimal getV3B() {
        return v3B;
    }

    @Transactional
    public void query() {
        Session session = SessionFactoryUtils.getSession(sessionFactory, false);

        //货资资金
        Value1A value1A = new Value1A();
        value1A.setDate(queryDate);
        value1A.setVerified(verified);
        value1A.setSession(session);
        value1A.calc();
        v1A = value1A.getValue();

        Value1B value1B = new Value1B();
        value1B.setDate(queryDate);
        value1B.setVerified(verified);
        value1B.setSession(session);
        value1B.calc();
        v1B = value1B.getValue();

        //短期投资
        Value2A value2A = new Value2A();
        value2A.setDate(queryDate);
        value2A.setVerified(verified);
        value2A.setSession(session);
        value2A.calc();
        v2A = value2A.getValue();

        Value2B value2B = new Value2B();
        value2B.setDate(queryDate);
        value2B.setVerified(verified);
        value2B.setSession(session);
        value2B.calc();
        v2B = value2B.getValue();

        //应收票据
        Value3A value3A = new Value3A();
        value3A.setDate(queryDate);
        value3A.setVerified(verified);
        value3A.setSession(session);
        value3A.calc();
        v3A = value3A.getValue();

        Value3B value3B = new Value3B();
        value3B.setDate(queryDate);
        value3B.setVerified(verified);
        value3B.setSession(session);
        value3B.calc();
        v3B = value3B.getValue();


    }

}
