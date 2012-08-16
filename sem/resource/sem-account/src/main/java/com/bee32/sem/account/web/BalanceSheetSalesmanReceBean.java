package com.bee32.sem.account.web;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.annotation.Transactional;

public class BalanceSheetSalesmanReceBean
        extends AbstractAccountEVB {

    private static final long serialVersionUID = 1L;

    @Inject
    SessionFactory sessionFactory;

    boolean verified;

    List<Object[]> result = new ArrayList<Object[]>();
    List<Object[]> summary = new ArrayList<Object[]>();

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public List<Object[]> getResult() {
        return result;
    }

    public void setResult(List<Object[]> result) {
        this.result = result;
    }

    public List<Object[]> getSummary() {
        return summary;
    }

    public void setSummary(List<Object[]> summary) {
        this.summary = summary;
    }

    @Transactional(readOnly = readOnlyTxEnabled)
    public void query() {
        String sql1 = getBundledSQL("1", //
                "AND_VERIFIED", verified ? "and verify_eval_state=33554434" : null);

        Session session = SessionFactoryUtils.getSession(sessionFactory, false);
        SQLQuery sqlQuery = session.createSQLQuery(sql1);
        result = sqlQuery.list();

        String sql2 = getBundledSQL("2", //
                "AND_VERIFIED", verified ? "and verify_eval_state=33554434" : null);

        sqlQuery = session.createSQLQuery(sql2);
        summary = sqlQuery.list();
    }
}
