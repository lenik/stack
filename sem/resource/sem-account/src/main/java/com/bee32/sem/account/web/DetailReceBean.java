package com.bee32.sem.account.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.annotation.Transactional;

public class DetailReceBean
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

        Integer index = 0;
        BigDecimal sumRable = new BigDecimal(0);
        BigDecimal sumRed = new BigDecimal(0);

        while (index < result.size()) {
            Calendar beginTime = Calendar.getInstance();
            beginTime.setTime((Date) result.get(index)[1]);
            if (beginTime.get(Calendar.YEAR) == 2000 && beginTime.get(Calendar.MONTH) == 0
                    && beginTime.get(Calendar.DAY_OF_MONTH) == 1) {
                // 判断是否为2000-01-01,如果是，则界面上显示为空
                result.get(index)[1] = "";
            }

            String lastParty = index > 0 ? (String) result.get(index - 1)[2] : "";
            String currParty = (String) result.get(index)[2];

            BigDecimal lastBalance = index > 0 ? (BigDecimal) result.get(index - 1)[8] : new BigDecimal(0);
            BigDecimal rable = (BigDecimal) result.get(index)[6];
            BigDecimal red = (BigDecimal) result.get(index)[7];

            sumRable = sumRable.add(rable);
            sumRed = sumRed.add(red);

            if (!lastParty.equals(currParty)) {
                // 当前行是第一行或换一个公司
                lastBalance = (BigDecimal) result.get(index)[8];

                if (index > 0) {
                    // 加上合计行
                    Object[] row = new Object[9];
                    row[1] = "========";
                    row[2] = "合计";
                    row[3] = "========";
                    row[6] = sumRable;
                    row[7] = sumRed;
                    row[8] = (BigDecimal) result.get(index - 1)[8];
                    result.add(index, row);
                    index++;

                    sumRable = new BigDecimal(0);
                    sumRed = new BigDecimal(0);
                }
            }

            result.get(index)[8] = lastBalance.add(rable).subtract(red);

            index++;
        }

        // ********************************
        // 最后补一行合计行
        Object[] row = new Object[9];
        row[1] = "========";
        row[2] = "合计";
        row[3] = "========";
        row[6] = sumRable;
        row[7] = sumRed;
        row[8] = (BigDecimal) result.get(index - 1)[8];
        result.add(index, row);
        // ********************************
    }
}
