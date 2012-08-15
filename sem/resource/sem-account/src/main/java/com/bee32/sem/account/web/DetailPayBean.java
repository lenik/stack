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

import com.bee32.plover.orm.util.EntityViewBean;

public class DetailPayBean
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

    @Transactional(readOnly = Config.readOnlyTxEnabled)
    public void query() {
        StringBuilder sb = new StringBuilder();

        sb.append("select a1.party as partyId,a1.begin_time,b.label as partyName,a1.label,a1.t,a1.bill_no,a1.pable,a1.ped,a1.balance from ( ");
        sb.append("    select to_date('2000-01-01','yyyy-MM-dd') as begin_time,party,label,'期初' as t,bill_no,0 as pable,0 as ped,amount as balance  ");
        sb.append("    from current_account  ");
        sb.append("    where stereo='PINIT' ");
        if (verified) {
            sb.append("     and verify_eval_state=33554434 ");
        }
        sb.append("    union all ");
        sb.append("    select begin_time,party,label,'应收单' as t,bill_no,amount as pable,0 as ped,0 as balance  ");
        sb.append("    from current_account  ");
        sb.append("    where stereo='PABLE' ");
        if (verified) {
            sb.append("     and verify_eval_state=33554434 ");
        }
        sb.append("    union all ");
        sb.append("    select begin_time,party,label,'收款单' as t,bill_no,0 as pable,-amount as ped,0 as balance  ");
        sb.append("    from current_account  ");
        sb.append("    where stereo='PED' ");
        if (verified) {
            sb.append("     and verify_eval_state=33554434 ");
        }
        sb.append(") a1  ");
        sb.append("left join party b ");
        sb.append("    on a1.party=b.id ");
        sb.append("order by a1.party,a1.begin_time ");

        Session session = SessionFactoryUtils.getSession(sessionFactory, false);
        SQLQuery sqlQuery = session.createSQLQuery(sb.toString());
        result = sqlQuery.list();

        Integer index = 0;
        BigDecimal sumPable = new BigDecimal(0);
        BigDecimal sumPed = new BigDecimal(0);

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
            BigDecimal pable = (BigDecimal) result.get(index)[6];
            BigDecimal ped = (BigDecimal) result.get(index)[7];

            sumPable = sumPable.add(pable);
            sumPed = sumPed.add(ped);

            if (!lastParty.equals(currParty)) {
                // 当前行是第一行或换一个公司
                lastBalance = (BigDecimal) result.get(index)[8];

                if (index > 0) {
                    // 加上合计行
                    Object[] row = new Object[9];
                    row[1] = "========";
                    row[2] = "合计";
                    row[3] = "========";
                    row[6] = sumPable;
                    row[7] = sumPed;
                    row[8] = (BigDecimal) result.get(index - 1)[8];
                    result.add(index, row);
                    index++;

                    sumPable = new BigDecimal(0);
                    sumPed = new BigDecimal(0);
                }
            }

            result.get(index)[8] = lastBalance.add(pable).subtract(ped);

            index++;
        }

        // ********************************
        // 最后补一行合计行
        Object[] row = new Object[9];
        row[1] = "========";
        row[2] = "合计";
        row[3] = "========";
        row[6] = sumPable;
        row[7] = sumPed;
        row[8] = (BigDecimal) result.get(index - 1)[8];
        result.add(index, row);
        // ********************************
    }
}
