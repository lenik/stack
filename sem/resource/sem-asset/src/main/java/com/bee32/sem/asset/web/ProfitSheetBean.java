package com.bee32.sem.asset.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue10A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue10B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue1A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue1B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue2A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue2B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue3A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue3B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue4A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue4B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue5A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue5B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue6A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue6B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue7A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue7B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue8A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue8B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue9A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue9B;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.service.PeopleService;

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

    BigDecimal v4A;
    BigDecimal v4B;

    BigDecimal v5A;
    BigDecimal v5B;

    BigDecimal v6A;
    BigDecimal v6B;

    BigDecimal v7A;
    BigDecimal v7B;

    BigDecimal v8A;
    BigDecimal v8B;

    BigDecimal v9A;
    BigDecimal v9B;

    BigDecimal v10A;
    BigDecimal v10B;

    public String getSelfOrg() {
        Org selfOrg = BEAN(PeopleService.class).getSelfOrg();

        return selfOrg.getDisplayName();
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public String getQueryDateText() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(queryDate);
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

    public BigDecimal getV5A() {
        return v5A;
    }

    public BigDecimal getV5B() {
        return v5B;
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

    public BigDecimal getV10A() {
        return v10A;
    }

    public BigDecimal getV10B() {
        return v10B;
    }

    @Transactional
    public void query() {
        Session session = SessionFactoryUtils.getSession(sessionFactory, false);

        ProfitValue1A value1A = new ProfitValue1A();
        value1A.setDate(queryDate);
        value1A.setVerified(verified);
        value1A.setSession(session);
        value1A.calc();
        v1A = value1A.getValue();

        ProfitValue1B value1B = new ProfitValue1B();
        value1B.setDate(queryDate);
        value1B.setVerified(verified);
        value1B.setSession(session);
        value1B.calc();
        v1B = value1B.getValue();



        ProfitValue2A value2A = new ProfitValue2A();
        value2A.setDate(queryDate);
        value2A.setVerified(verified);
        value2A.setSession(session);
        value2A.calc();
        v2A = value2A.getValue();

        ProfitValue2B value2B = new ProfitValue2B();
        value2B.setDate(queryDate);
        value2B.setVerified(verified);
        value2B.setSession(session);
        value2B.calc();
        v2B = value2B.getValue();



        ProfitValue3A value3A = new ProfitValue3A();
        value3A.setDate(queryDate);
        value3A.setVerified(verified);
        value3A.setSession(session);
        value3A.calc();
        v3A = value3A.getValue();

        ProfitValue3B value3B = new ProfitValue3B();
        value3B.setDate(queryDate);
        value3B.setVerified(verified);
        value3B.setSession(session);
        value3B.calc();
        v3B = value3B.getValue();

        ProfitValue4A value4A = new ProfitValue4A();
        value4A.setDate(queryDate);
        value4A.setVerified(verified);
        value4A.setSession(session);
        value4A.calc();
        v4A = value4A.getValue();

        ProfitValue4B value4B = new ProfitValue4B();
        value4B.setDate(queryDate);
        value4B.setVerified(verified);
        value4B.setSession(session);
        value4B.calc();
        v4B = value4B.getValue();

        ProfitValue5A value5A = new ProfitValue5A();
        value5A.setDate(queryDate);
        value5A.setVerified(verified);
        value5A.setSession(session);
        value5A.calc();
        v5A = value5A.getValue();

        ProfitValue5B value5B = new ProfitValue5B();
        value5B.setDate(queryDate);
        value5B.setVerified(verified);
        value5B.setSession(session);
        value5B.calc();
        v5B = value5B.getValue();

        ProfitValue6A value6A = new ProfitValue6A();
        value6A.setDate(queryDate);
        value6A.setVerified(verified);
        value6A.setSession(session);
        value6A.calc();
        v6A = value6A.getValue();

        ProfitValue6B value6B = new ProfitValue6B();
        value6B.setDate(queryDate);
        value6B.setVerified(verified);
        value6B.setSession(session);
        value6B.calc();
        v6B = value6B.getValue();

        ProfitValue7A value7A = new ProfitValue7A();
        value7A.setDate(queryDate);
        value7A.setVerified(verified);
        value7A.setSession(session);
        value7A.calc();
        v7A = value7A.getValue();

        ProfitValue7B value7B = new ProfitValue7B();
        value7B.setDate(queryDate);
        value7B.setVerified(verified);
        value7B.setSession(session);
        value7B.calc();
        v7B = value7B.getValue();

        ProfitValue8A value8A = new ProfitValue8A();
        value8A.setDate(queryDate);
        value8A.setVerified(verified);
        value8A.setSession(session);
        value8A.calc();
        v8A = value8A.getValue();

        ProfitValue8B value8B = new ProfitValue8B();
        value8B.setDate(queryDate);
        value8B.setVerified(verified);
        value8B.setSession(session);
        value8B.calc();
        v8B = value8B.getValue();

        ProfitValue9A value9A = new ProfitValue9A();
        value9A.setDate(queryDate);
        value9A.setVerified(verified);
        value9A.setSession(session);
        value9A.calc();
        v9A = value9A.getValue();

        ProfitValue9B value9B = new ProfitValue9B();
        value9B.setDate(queryDate);
        value9B.setVerified(verified);
        value9B.setSession(session);
        value9B.calc();
        v9B = value9B.getValue();

        ProfitValue10A value10A = new ProfitValue10A();
        value10A.setDate(queryDate);
        value10A.setVerified(verified);
        value10A.setSession(session);
        value10A.calc();
        v10A = value10A.getValue();

        ProfitValue10B value10B = new ProfitValue10B();
        value10B.setDate(queryDate);
        value10B.setVerified(verified);
        value10B.setSession(session);
        value10B.calc();
        v10B = value10B.getValue();

    }

}
