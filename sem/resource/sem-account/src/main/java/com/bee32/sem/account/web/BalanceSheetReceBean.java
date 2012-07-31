package com.bee32.sem.account.web;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.util.EntityViewBean;

public class BalanceSheetReceBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    @Inject
    SessionFactory sessionFactory;

    boolean verified;

    List<Object[]> result = new ArrayList<Object[]>();

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

    @Transactional
    // (readOnly = true)
    public void query() {
        StringBuilder sb = new StringBuilder();

        sb.append("select  ");
        sb.append(" a2.party as partyId,b.label as partyName, ");
        sb.append(" a2.init,a2.rable,a2.red,a2.init+a2.rable+a2.red as balance from ( ");
        sb.append(" select party,sum(init) as init,sum(rable) as rable,sum(red) as red, sum(init)+sum(rable)+sum(red) as balance from ( ");
        sb.append("     select party,amount as init,0 as rable,0 as red from current_account  ");
        sb.append("     where stereo='RINIT' ");
        if(verified) {
            sb.append("     and verify_eval_state=33554434 ");
        }
        sb.append("     union all ");
        sb.append("     select party,0 as init, sum(amount) as rable, 0 as red from current_account  ");
        sb.append("     where stereo='RABLE'  ");
        if(verified) {
            sb.append("     and verify_eval_state=33554434 ");
        }
        sb.append("     group by party ");
        sb.append("     union all ");
        sb.append("     select party,0 as init, 0 as rable, sum(amount) as red from current_account  ");
        sb.append("     where stereo='RED'  ");
        if(verified) {
            sb.append("     and verify_eval_state=33554434 ");
        }
        sb.append("     group by party ");
        sb.append(" ) a1 group by party ");
        sb.append(") a2 ");
        sb.append("left join party b ");
        sb.append(" on a2.party=b.id ");

        Session session = SessionFactoryUtils.getSession(sessionFactory, false);
        SQLQuery sqlQuery = session.createSQLQuery(sb.toString());

        result = sqlQuery.list();
    }
}
