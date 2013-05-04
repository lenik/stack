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
import com.bee32.sem.asset.service.balance_sheet.Value33A;
import com.bee32.sem.asset.service.balance_sheet.Value33B;
import com.bee32.sem.asset.service.balance_sheet.Value34A;
import com.bee32.sem.asset.service.balance_sheet.Value34B;
import com.bee32.sem.asset.service.balance_sheet.Value3A;
import com.bee32.sem.asset.service.balance_sheet.Value3B;
import com.bee32.sem.asset.service.balance_sheet.Value4A;
import com.bee32.sem.asset.service.balance_sheet.Value4B;
import com.bee32.sem.asset.service.balance_sheet.Value5A;
import com.bee32.sem.asset.service.balance_sheet.Value5B;
import com.bee32.sem.asset.service.balance_sheet.Value6A;
import com.bee32.sem.asset.service.balance_sheet.Value6B;
import com.bee32.sem.asset.service.balance_sheet.Value7A;
import com.bee32.sem.asset.service.balance_sheet.Value7B;
import com.bee32.sem.asset.service.balance_sheet.Value8A;
import com.bee32.sem.asset.service.balance_sheet.Value8B;
import com.bee32.sem.asset.service.balance_sheet.Value9A;
import com.bee32.sem.asset.service.balance_sheet.Value9B;

public class BalanceSheetBean
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

    BigDecimal v4A;
    BigDecimal v4B;

    BigDecimal v34A;
    BigDecimal v34B;

    BigDecimal v5A;
    BigDecimal v5B;

    BigDecimal v33A;
    BigDecimal v33B;

    BigDecimal v6A;
    BigDecimal v6B;

    BigDecimal v7A;
    BigDecimal v7B;

    BigDecimal v8A;
    BigDecimal v8B;

    BigDecimal v9A;
    BigDecimal v9B;

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

    public BigDecimal getV4A() {
        return v4A;
    }

    public BigDecimal getV4B() {
        return v4B;
    }

    public BigDecimal getV34A() {
        return v34A;
    }

    public BigDecimal getV34B() {
        return v34B;
    }

    public BigDecimal getV5A() {
        return v5A;
    }

    public BigDecimal getV5B() {
        return v5B;
    }

    public BigDecimal getV33A() {
        return v33A;
    }

    public BigDecimal getV33B() {
        return v33B;
    }

    public BigDecimal getV6A() {
        return v6A;
    }

    public BigDecimal getV6B() {
        return v6B;
    }

    public BigDecimal getV7A() {
        return v7A;
    }

    public BigDecimal getV7B() {
        return v7B;
    }

    public BigDecimal getV8A() {
        return v8A;
    }

    public BigDecimal getV8B() {
        return v8B;
    }

    public BigDecimal getV9A() {
        return v9A;
    }

    public BigDecimal getV9B() {
        return v9B;
    }

    @Transactional
    public void query() {
        Session session = SessionFactoryUtils.getSession(sessionFactory, false);

        //货币资金
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

        //应收账款
        Value4A value4A = new Value4A();
        value4A.setDate(queryDate);
        value4A.setVerified(verified);
        value4A.setSession(session);
        value4A.calc();
        v4A = value4A.getValue();

        Value4B value4B = new Value4B();
        value4B.setDate(queryDate);
        value4B.setVerified(verified);
        value4B.setSession(session);
        value4B.calc();
        v4B = value4B.getValue();

        //预收账款
        Value34A value34A = new Value34A();
        value34A.setDate(queryDate);
        value34A.setVerified(verified);
        value34A.setSession(session);
        value34A.calc();
        v34A = value34A.getValue();

        Value34B value34B = new Value34B();
        value34B.setDate(queryDate);
        value34B.setVerified(verified);
        value34B.setSession(session);
        value34B.calc();
        v34B = value34B.getValue();

        //预付账款
        Value5A value5A = new Value5A();
        value5A.setDate(queryDate);
        value5A.setVerified(verified);
        value5A.setSession(session);
        value5A.calc();
        v5A = value5A.getValue();

        Value5B value5B = new Value5B();
        value5B.setDate(queryDate);
        value5B.setVerified(verified);
        value5B.setSession(session);
        value5B.calc();
        v5B = value5B.getValue();

        //应付账款
        Value33A value33A = new Value33A();
        value33A.setDate(queryDate);
        value33A.setVerified(verified);
        value33A.setSession(session);
        value33A.calc();
        v33A = value33A.getValue();

        Value33B value33B = new Value33B();
        value33B.setDate(queryDate);
        value33B.setVerified(verified);
        value33B.setSession(session);
        value33B.calc();
        v33B = value33B.getValue();

        //应收股利
        Value6A value6A = new Value6A();
        value6A.setDate(queryDate);
        value6A.setVerified(verified);
        value6A.setSession(session);
        value6A.calc();
        v6A = value6A.getValue();

        Value6B value6B = new Value6B();
        value6B.setDate(queryDate);
        value6B.setVerified(verified);
        value6B.setSession(session);
        value6B.calc();
        v6B = value6B.getValue();

        //应收利息
        Value7A value7A = new Value7A();
        value7A.setDate(queryDate);
        value7A.setVerified(verified);
        value7A.setSession(session);
        value7A.calc();
        v7A = value7A.getValue();

        Value7B value7B = new Value7B();
        value7B.setDate(queryDate);
        value7B.setVerified(verified);
        value7B.setSession(session);
        value7B.calc();
        v7B = value7B.getValue();

        //其他应收款
        Value8A value8A = new Value8A();
        value8A.setDate(queryDate);
        value8A.setVerified(verified);
        value8A.setSession(session);
        value8A.calc();
        v8A = value8A.getValue();

        Value8B value8B = new Value8B();
        value8B.setDate(queryDate);
        value8B.setVerified(verified);
        value8B.setSession(session);
        value8B.calc();
        v8B = value8B.getValue();

        //存货
        Value9A value9A = new Value9A();
        value9A.setDate(queryDate);
        value9A.setVerified(verified);
        value9A.setSession(session);
        value9A.calc();
        v9A = value9A.getValue();

        Value9B value9B = new Value9B();
        value9B.setDate(queryDate);
        value9B.setVerified(verified);
        value9B.setSession(session);
        value9B.calc();
        v9B = value9B.getValue();
    }

}
