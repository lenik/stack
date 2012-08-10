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

public class BalanceSheetSalesmanPayBean
        extends EntityViewBean {

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

    @SuppressWarnings("unchecked")
    @Transactional
    // (readOnly = true)
    public void query() {
        StringBuilder sb = new StringBuilder();

        sb.append("select    ");
        sb.append("    a2.person as personId,p.label as personName, ");
        sb.append("    a2.party as partyId,b.label as partyName,   ");
        sb.append("    a2.init,a2.pable,a2.ped,a2.balance  ");
        sb.append("from (   ");
        sb.append("    select  ");
        sb.append("        person,party,sum(init) as init,sum(pable) as pable,sum(ped) as ped,  ");
        sb.append("        sum(init)+sum(pable)-sum(ped) as balance  ");
        sb.append("    from (       ");
        sb.append("        select person,party,amount as init,0 as pable,0 as ped  ");
        sb.append("        from current_account        ");
        sb.append("        where stereo='PINIT'       ");
        if(verified) {
            sb.append("     and verify_eval_state=33554434 ");
        }
        sb.append("        union all       ");
        sb.append("        select person,party,0 as init, sum(amount) as pable, 0 as ped  ");
        sb.append("        from current_account        ");
        sb.append("        where stereo='PABLE'        ");
        if(verified) {
            sb.append("     and verify_eval_state=33554434 ");
        }
        sb.append("        group by person,party       ");
        sb.append("        union all       ");
        sb.append("        select person,party,0 as init, 0 as pable, -sum(amount) as ped  ");
        sb.append("        from current_account        ");
        sb.append("        where stereo='PED'        ");
        if(verified) {
            sb.append("     and verify_eval_state=33554434 ");
        }
        sb.append("        group by person,party   ");
        sb.append("    ) a1  ");
        sb.append("    group by person,party  ");
        sb.append(") a2  ");
        sb.append("left join party p ");
        sb.append("    on a2.person=p.id ");
        sb.append("left join party b   ");
        sb.append("    on a2.party=b.id ");

        Session session = SessionFactoryUtils.getSession(sessionFactory, false);
        SQLQuery sqlQuery = session.createSQLQuery(sb.toString());
        result = sqlQuery.list();


        sb.delete(0, sb.length());  //清空string builder
        sb.append("select  ");
        sb.append(" sum(init) as init,sum(pable) as pable,sum(ped) as ped, sum(init)+sum(pable)-sum(ped) as balance from ( ");
        sb.append(" select amount as init,0 as pable,0 as ped from current_account  ");
        sb.append(" where stereo='PINIT' ");
        if(verified) {
            sb.append("     and verify_eval_state=33554434 ");
        }
        sb.append(" union all ");
        sb.append(" select 0 as init, sum(amount) as pable, 0 as ped from current_account  ");
        sb.append(" where stereo='PABLE'  ");
        if(verified) {
            sb.append("     and verify_eval_state=33554434 ");
        }
        sb.append(" union all ");
        sb.append(" select 0 as init, 0 as pable, -sum(amount) as ped from current_account  ");
        sb.append(" where stereo='PED' ");
        if(verified) {
            sb.append("     and verify_eval_state=33554434 ");
        }
        sb.append(") a1  ");

        sqlQuery = session.createSQLQuery(sb.toString());
        summary = sqlQuery.list();
    }
}
