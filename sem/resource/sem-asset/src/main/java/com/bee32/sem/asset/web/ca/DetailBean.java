package com.bee32.sem.asset.web.ca;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.util.EntityViewBean;

public class DetailBean
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

    @Transactional(readOnly = true)
    public void query() {
        String sql1 = getBundledSQL("1", //
                "AND_VERIFIED", verified ? "and verify_eval_state=33554434" : null);

        Session session = SessionFactoryUtils.getSession(sessionFactory, false);
        SQLQuery sqlQuery = session.createSQLQuery(sql1);
        result = sqlQuery.list();

        Integer index = 0;
        BigDecimal sumPlus = new BigDecimal(0);
        BigDecimal sumNeg = new BigDecimal(0);

        while (index < result.size()) {
            String lastParty = index > 0 ? (String) result.get(index - 1)[1] : "";
            String currParty = (String) result.get(index)[1];

            BigDecimal lastBalance = index > 0 ? (BigDecimal) result.get(index - 1)[6] : new BigDecimal(0);
            BigDecimal plus = (BigDecimal) result.get(index)[4];
            BigDecimal neg = (BigDecimal) result.get(index)[5];

            if (!lastParty.equals(currParty)) {
                // 当前行是第一行或换一个往来对象
                lastBalance = (BigDecimal) result.get(index)[6];

                if (index > 0) {
                    // 加上合计行
                    Object[] row = new Object[7];
                    row[1] = "========";
                    row[2] = "合计";
                    row[3] = "========";
                    row[4] = sumPlus;
                    row[5] = sumNeg;
                    row[6] = (BigDecimal) result.get(index - 1)[6];
                    result.add(index, row);
                    index++;

                    sumPlus = new BigDecimal(0);
                    sumNeg = new BigDecimal(0);
                }
            }

            sumPlus = sumPlus.add(plus);
            sumNeg = sumNeg.add(neg);

            result.get(index)[6] = lastBalance.add(plus).subtract(neg);

            index++;
        }

        // ********************************
        // 最后补一行合计行
        Object[] row = new Object[7];
        row[1] = "========";
        row[2] = "合计";
        row[3] = "========";
        row[4] = sumPlus;
        row[5] = sumNeg;
        row[6] = (BigDecimal) result.get(index - 1)[6];
        result.add(index, row);
        // ********************************
    }
}
