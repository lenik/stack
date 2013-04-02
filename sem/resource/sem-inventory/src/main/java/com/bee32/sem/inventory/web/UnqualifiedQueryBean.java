package com.bee32.sem.inventory.web;

import java.math.BigDecimal;
import java.util.*;

import javax.inject.Inject;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.inventory.entity.StockInventory;
import com.bee32.sem.inventory.entity.StockItemState;
import com.bee32.sem.inventory.web.business.StockDictsBean;
import com.bee32.sem.material.dto.StockWarehouseDto;

@ForEntity(StockInventory.class)
public class UnqualifiedQueryBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    @Inject
    StockDictsBean dicts;

    @Inject
    SessionFactory sessionFactory;

    Integer selectedWarehouseId = -1;
    Date queryDateFrom = new Date();
    Date queryDateTo = new Date();

    boolean verified;

    List<QualifiedHolder> result = new ArrayList<QualifiedHolder>();

    public int getSelectedWarehouseId() {
        return selectedWarehouseId;
    }

    public void setSelectedWarehouseId(int selectedWarehouseId) {
        if (this.selectedWarehouseId != selectedWarehouseId) {
            this.selectedWarehouseId = selectedWarehouseId;
        }
    }

    public StockWarehouseDto getSelectedWarehouse() {
        if (selectedWarehouseId == -1)
            return null;
        else
            return dicts.getWarehouse(selectedWarehouseId);
    }

    public Date getQueryDateFrom() {
        return queryDateFrom;
    }

    public void setQueryDateFrom(Date queryDateFrom) {
        this.queryDateFrom = queryDateFrom;
    }

    public Date getQueryDateTo() {
        return queryDateTo;
    }

    public void setQueryDateTo(Date queryDateTo) {
        this.queryDateTo = queryDateTo;
    }

    public List<QualifiedHolder> getResult() {
        return result;
    }

    public void setResult(List<QualifiedHolder> result) {
        this.result = result;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Transactional
    // (readOnly = true)
    public void query() {
        if (selectedWarehouseId == -1) {
            uiLogger.error("未选择${tr.inventory.warehouse}!");
            return;
        }

        // 设为 0:0:0.000
        Calendar calFrom = Calendar.getInstance();
        calFrom.setTime(queryDateFrom);
        calFrom.set(Calendar.HOUR_OF_DAY, 0);
        calFrom.set(Calendar.MINUTE, 0);
        calFrom.set(Calendar.SECOND, 0);
        calFrom.set(Calendar.MILLISECOND, 0);

        // 设为 23:59:59.999
        Calendar calTo = Calendar.getInstance();
        calTo.setTime(queryDateTo);
        calTo.set(Calendar.HOUR_OF_DAY, 23);
        calTo.set(Calendar.MINUTE, 59);
        calTo.set(Calendar.SECOND, 59);
        calTo.set(Calendar.MILLISECOND, 999);

        StringBuilder sb = new StringBuilder();

        sb.append("select ");
        sb.append("    date_trunc('day', a.created_date) as d_day, ");
        sb.append("	   a.stateInt, ");
        sb.append("	   sum(a.quantity) as quantity ");
        sb.append("from stock_order_item a ");
        sb.append("left join stock_order b ");
        sb.append(" on a.parent=b.id ");
        sb.append("where ");
        sb.append("	   a.warehouse=:warehouseId ");
        sb.append("	   and a.created_date between :dateFrom and :dateTo ");
        if(verified) {
            sb.append("and b.verify_eval_state=33554434 ");
        }
        sb.append("group by ");
        sb.append("	   date_trunc('day', a.created_date), ");
        sb.append("	   a.stateInt ");
        sb.append("order by date_trunc('day', a.created_date) ");

        Session session = SessionFactoryUtils.getSession(sessionFactory, false);
        SQLQuery sqlQuery = session.createSQLQuery(sb.toString());
        sqlQuery.setParameter("warehouseId", selectedWarehouseId);
        sqlQuery.setParameter("dateFrom", calFrom.getTime());
        sqlQuery.setParameter("dateTo", calTo.getTime());

        List<Object[]> ret = sqlQuery.list();

        Map<Long, QualifiedHolder> holders = new HashMap<Long, QualifiedHolder>();

        // 以while语句形成指定时间范围内并包含每一天的一个表
        calTo.set(Calendar.HOUR_OF_DAY, 0);
        calTo.set(Calendar.MINUTE, 0);
        calTo.set(Calendar.SECOND, 0);
        calTo.set(Calendar.MILLISECOND, 0);
        while (calFrom.compareTo(calTo) <= 0) {
            QualifiedHolder holder = new QualifiedHolder();
            holder.setDate(calFrom.getTime());
            holders.put(calFrom.getTime().getTime(), holder);

            calFrom.add(Calendar.DAY_OF_MONTH, 1);
        }

        for (Object[] row : ret) {
            QualifiedHolder holder = holders.get(((Date) row[0]).getTime());

            if (((char) row[1]) == StockItemState.QUALIFIED.getValue()) {
                // 合格数量
                holder.setQualified((BigDecimal) row[2]);
            } else {
                // 不合格数量
                holder.setUnqualified((BigDecimal) row[2]);
            }

            if (holder.getQualified() != null && holder.getUnqualified() != null) {
                holder.setTotal(holder.getQualified().add(holder.getUnqualified()));
                double total = holder.getTotal().doubleValue();
                double unqualified = holder.getUnqualified().doubleValue();

                holder.setUnqualifiedRatio(unqualified / total);
            }
        }

        result = new ArrayList<>(holders.values());
        Collections.sort(result);
    }

}
