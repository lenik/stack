package com.bee32.sem.account.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.annotation.Transactional;

public class VerificationQueryPayBean
        extends AbstractAccountEVB {

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

    @Transactional(readOnly = readOnlyTxEnabled)
    public void query() {
        result = new ArrayList<Object[]>();

        StringBuilder sb = new StringBuilder();

        sb.append("select a.party as party_id,b.label as party_name,a.begin_time,a.expected_date,a.amount,a.id as able_id  ");
        sb.append("from current_account a ");
        sb.append("left join party b ");
        sb.append("    on a.party=b.id ");
        sb.append("where (a.stereo='PINIT' or a.stereo='PABLE') ");
        if (verified) {
            sb.append("     and a.verify_eval_state=33554434 ");
        }
        sb.append("order by a.party,a.begin_time ");

        Session session = SessionFactoryUtils.getSession(sessionFactory, false);
        SQLQuery sqlQuery = session.createSQLQuery(sb.toString());
        List<Object[]> ableList = sqlQuery.list();

        Integer index = 0;

        while (index < ableList.size()) {
            sb.delete(0, sb.length());

            sb.append("select a.begin_time,a.amount,b.begin_time,b.amount from verification a ");
            sb.append("left join current_account b ");
            sb.append("    on a.account_ed=b.id ");
            sb.append("where a.account_able=:account_able ");
            sb.append("order by a.begin_time ");

            sqlQuery = session.createSQLQuery(sb.toString());
            sqlQuery.setParameter("account_able", ableList.get(index)[5]);
            List<Object[]> verificationList = sqlQuery.list();

            BigDecimal havenotVerification = (BigDecimal) ableList.get(index)[4];
            for (Object[] verificationRow : verificationList) {
                Object[] row = new Object[10];

                row[0] = ableList.get(index)[0];
                row[1] = ableList.get(index)[1];
                row[2] = ableList.get(index)[2];
                row[3] = ableList.get(index)[3];
                row[4] = ableList.get(index)[4];

                row[5] = verificationRow[0];
                row[6] = verificationRow[1];
                row[7] = verificationRow[2];
                row[8] = verificationRow[3];

                BigDecimal verificationAmount = (BigDecimal) verificationRow[1];

                havenotVerification = havenotVerification.subtract(verificationAmount);
                if (havenotVerification.compareTo(new BigDecimal(0)) <= 0) {
                    havenotVerification = new BigDecimal(0);
                }
                row[9] = havenotVerification;

                result.add(row);
            }

            index++;
        }
    }
}
