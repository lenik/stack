package com.bee32.sem.world.monetary.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.Limit;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.sem.world.monetary.AbstractFxrProvider;
import com.bee32.sem.world.monetary.FxrCriteria;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.FxrRecord;
import com.bee32.sem.world.monetary.FxrTable;

/**
 * 离散量外汇查询
 * <ol>
 * <li>汇率根据离散时间点上的汇率推算
 * <li>同一日期的汇率构成一张汇率表
 * <li>nprev 指定汇率表的张数
 * <li>只取历史汇率。取时间点 t 的汇率时，仅参考 t、t-1、t-2 的汇率。即： 不参考 t+1 的汇率。
 * <li>汇率表的大小自动调整
 * <li>缓存当天的汇率
 * <li><i>缓存经常查询的历史汇率</i>
 * </ol>
 */
public class DiscreteFxrProvider
        extends AbstractFxrProvider {

    static Logger logger = LoggerFactory.getLogger(DiscreteFxrProvider.class);

    static final int INITIAL_TABLE_SIZE = 100;
    static final int MIN_LIMIT = 120;

    int appxTableSize = INITIAL_TABLE_SIZE;

    static int NPREV_NORM = 10;
    TreeMap<Date, List<FxrTable>> fxrtabsCache = new TreeMap<Date, List<FxrTable>>();

    @Override
    public synchronized List<FxrTable> getFxrTableSeries(Date queryDate, int nprev)
            throws FxrQueryException {

        if (queryDate == null)
            throw new NullPointerException("queryDate");
        if (nprev < 1)
            throw new IllegalArgumentException("Bad nprev: " + nprev);

        if (nprev < NPREV_NORM)
            nprev = NPREV_NORM;

        List<FxrTable> series = new ArrayList<FxrTable>();

        if (nprev == NPREV_NORM) {
            series = fxrtabsCache.get(queryDate);
            if (series != null)
                return series;
            else
                fxrtabsCache.put(queryDate, series);
        }

        int limit = appxTableSize * nprev;
        if (limit < MIN_LIMIT)
            limit = MIN_LIMIT;

        List<FxrRecord> records = asFor(FxrRecord.class).list(//
                Order.desc("date"), //
                new Limit(0, limit), //
                FxrCriteria.beforeThan(queryDate, limit));
        Iterator<FxrRecord> iter = records.iterator();

        FxrTable table = null;
        Date lastDate = null;

        while (nprev > 0) {
            if (!iter.hasNext())
                break;
            FxrRecord record = iter.next();
            Date date = record.getDate();

            if (table == null) {
                table = new FxrTable();
                series.add(0, table); // insert in reverse order.
            } else {
                if (lastDate.equals(date)) {
                    table.put(record);
                } else {
                    table = null;
                    nprev--;
                }
            }
            lastDate = date;
        }

        return series;
    }

}
