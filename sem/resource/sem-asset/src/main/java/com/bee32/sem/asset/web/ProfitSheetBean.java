package com.bee32.sem.asset.web;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.util.EntityViewBean;

public class ProfitSheetBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    @Inject
    SessionFactory sessionFactory;

    Date queryDate = new Date();

    boolean verified;

    BigDecimal value1A;
    BigDecimal value1B;

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

    public BigDecimal getValue1A() {
        return value1A;
    }

    public BigDecimal getValue1B() {
        return value1B;
    }

    public void calcValue1A() {

        StringBuilder sb = new StringBuilder();

        sb.append("select sum(a.native_value) ");
        sb.append("from account_ticket_item a ");
        sb.append("left join account_ticket b ");
        sb.append("on a.ticket=b.id ");
        sb.append("where (a.subject like '1001%' or a.subject like '1002%' or a.subject like '1012%') ");
        sb.append("and (b.begin_time>=:beginOfYear and b.begin_time<=:dateTo) ");

        if(verified) {
            sb.append("     and b.verify_eval_state='33554433' ");
        }


    }


    @Transactional
    // (readOnly = true)
    public void query() {
        // 设为 23:59:59.999
        Calendar calTo = Calendar.getInstance();
        calTo.setTime(queryDate);
        calTo.set(Calendar.HOUR_OF_DAY, 23);
        calTo.set(Calendar.MINUTE, 59);
        calTo.set(Calendar.SECOND, 59);
        calTo.set(Calendar.MILLISECOND, 999);

        StringBuilder sb = new StringBuilder();







        sb.append("select ");
        sb.append("    a1.d_day, ");
        sb.append("    a1.material as material_id, ");
        sb.append("    c1.label as material_name, ");
        sb.append("    c1.model_spec as model_spec, ");
        sb.append("    a1.batch0, ");
        sb.append("    a1.batch1, ");
        sb.append("    a1.batch2, ");
        sb.append("    a1.batch3, ");
        sb.append("    b1.native_price as price0, ");
        sb.append("    a1.native_price as price1, ");
        sb.append("    -a1.quantity as quantity, ");
        sb.append("    (a1.native_price-b1.native_price)*abs(a1.quantity) as profit ");
        sb.append("from ( ");
        sb.append("    select ");
        sb.append("        date_trunc('day', a.created_date) as d_day, ");
        sb.append("        a.material, ");
        sb.append("        case when a.batch0 is null then '' else a.batch0 end as batch0, ");
        sb.append("        case when a.batch1 is null then '' else a.batch1 end as batch1, ");
        sb.append("        case when a.batch2 is null then '' else a.batch2 end as batch2, ");
        sb.append("        case when a.batch3 is null then '' else a.batch3 end as batch3, ");
        sb.append("        a.native_price,sum(a.quantity) as quantity ");
        sb.append("    from stock_order_item a ");
        sb.append("    left join stock_order b ");
        sb.append("        on a.parent=b.id ");
        sb.append("    where b.subject='TK_O' and a.warehouse=:warehouseId  ");
        if(verified) {
            sb.append("     and b.verify_eval_state='33554433' ");
        }
        sb.append("    group by a.created_date,a.material,a.batch0,a.batch1,a.batch2,a.batch3,a.native_price ");
        sb.append(") a1 left join ( ");
        sb.append("    select ");
        sb.append("        a.material, ");
        sb.append("        case when a.batch0 is null then '' else a.batch0 end as batch0, ");
        sb.append("        case when a.batch1 is null then '' else a.batch1 end as batch1, ");
        sb.append("        case when a.batch2 is null then '' else a.batch2 end as batch2, ");
        sb.append("        case when a.batch3 is null then '' else a.batch3 end as batch3, ");
        sb.append("        a.native_price ");
        sb.append("    from stock_order_item a ");
        sb.append("    left join stock_order b ");
        sb.append("        on a.parent=b.id ");
        sb.append("    where (b.subject='XFRI' or b.subject='TKFI') and a.warehouse=:warehouseId ");
        sb.append(") b1 ");
        sb.append("    on ");
        sb.append("        a1.material=b1.material ");
        sb.append("        and a1.batch0=b1.batch0 ");
        sb.append("        and a1.batch1=b1.batch1 ");
        sb.append("        and a1.batch2=b1.batch2 ");
        sb.append("        and a1.batch3=b1.batch3 ");
        sb.append("left join material c1 ");
        sb.append("    on a1.material=c1.id ");
        sb.append("where a1.d_day between :dateFrom and :dateTo ");
        sb.append("order by a1.d_day ");

        Session session = SessionFactoryUtils.getSession(sessionFactory, false);
        SQLQuery sqlQuery = session.createSQLQuery(sb.toString());
        sqlQuery.setParameter("dateTo", calTo.getTime());

    }

}
