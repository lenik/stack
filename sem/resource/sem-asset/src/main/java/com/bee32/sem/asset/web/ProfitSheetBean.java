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
import com.bee32.sem.asset.service.profit_sheet.ProfitValue1A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue1B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue2A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue2B;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue3A;
import com.bee32.sem.asset.service.profit_sheet.ProfitValue3B;
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

    }

}
