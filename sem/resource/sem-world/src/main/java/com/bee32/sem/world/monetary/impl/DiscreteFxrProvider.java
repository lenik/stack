package com.bee32.sem.world.monetary.impl;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.Limit;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.sem.world.monetary.AbstractFxrProvider;
import com.bee32.sem.world.monetary.FxrCriteria;
import com.bee32.sem.world.monetary.FxrMap;
import com.bee32.sem.world.monetary.FxrMapKey;
import com.bee32.sem.world.monetary.FxrRecord;
import com.bee32.sem.world.monetary.FxrTable;
import com.bee32.sem.world.monetary.FxrUsage;
import com.bee32.sem.world.monetary.IFxrMap;

/**
 * 离散量外汇查询
 * <ol>
 * <li>汇率根据离散时间点上的汇率推算
 * <li>同一日期的汇率构成一张汇率表
 * <li>nprev 指定汇率表的张数
 * <li>只取历史汇率。取时间点 t 的汇率时，仅参考 t、t-1、t-2 的汇率。即： 不参考 t+1 的汇率。
 * <li>汇率表的大小自动调整
 * <li>缓存当天的汇率
 * <li>缓存经常查询的历史汇率
 * </ol>
 */
public class DiscreteFxrProvider
        extends AbstractFxrProvider {

    static Logger logger = LoggerFactory.getLogger(DiscreteFxrProvider.class);

    static final int INITIAL_TABLE_SIZE = 100;
    static final int MIN_LIMIT = 120;

    static int NPREV_NORM = 10;

    private int appxTableSize = INITIAL_TABLE_SIZE;
    private TreeMap<Date, List<FxrTable>> fxrtabsCache = new TreeMap<Date, List<FxrTable>>();

    static Date lastUpdatedDate;

    Map<FxrMapKey, IFxrMap> fxrMaps = new HashMap<FxrMapKey, IFxrMap>();

    @Override
    public synchronized List<FxrTable> getFxrTableSeries(Date queryDate, int nprev) {

        if (queryDate == null)
            throw new NullPointerException("queryDate");
        if (nprev < 1)
            throw new IllegalArgumentException("Bad nprev: " + nprev);

        if (nprev < NPREV_NORM)
            nprev = NPREV_NORM;

        List<FxrTable> series = new ArrayList<FxrTable>();

        if (nprev == NPREV_NORM) {
            List<FxrTable> cached = fxrtabsCache.get(queryDate);
            if (cached != null)
                return cached;
            else
                fxrtabsCache.put(queryDate, cached = series);
        }

        int limit = appxTableSize * nprev;
        if (limit < MIN_LIMIT)
            limit = MIN_LIMIT;

        List<FxrRecord> records = ctx.data.access(FxrRecord.class).list(//
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

    @Override
    public synchronized IFxrMap getFxrMap(Currency unitCurrency, FxrUsage usage) {
        if (unitCurrency == null)
            throw new NullPointerException("unitCurrency");
        if (usage == null)
            throw new NullPointerException("usage");

        if (unitCurrency.equals(getQuoteCurrency()))
            return IFxrMap.IDENT;

        FxrMapKey key = new FxrMapKey(unitCurrency, usage);

        IFxrMap fxrMap = fxrMaps.get(key);
        if (fxrMap == null) {
            FxrMap _fxrMap = new FxrMap(unitCurrency, usage);
            fxrMaps.put(key, fxrMap = _fxrMap);
        }
        return fxrMap;
    }

    @Override
    @Transactional(readOnly = false)
    public synchronized void commit(FxrTable table) {

        Currency quoteCurrency = table.getQuoteCurrency();
        if (quoteCurrency != CurrencyConfig.getNative()) {
            String message = String.format("FXR table of bad quote currency: %s (Native: %s)", //
                    quoteCurrency, CurrencyConfig.getNative());
            throw new IllegalArgumentException(message);
        }

        if (lastUpdatedDate == null) {
            FxrRecord mostRecentRecord = ctx.data.access(FxrRecord.class).getFirst(Order.desc("date"));
            if (mostRecentRecord == null)
                lastUpdatedDate = new Date(0); // 1970-1-1
            else
                lastUpdatedDate = mostRecentRecord.getDate();
        }

        List<FxrRecord> newRecords = new ArrayList<FxrRecord>();
        Date maxDate = lastUpdatedDate;
        for (FxrRecord record : table.getRecords()) {
            // record.date > lastUpdatedDate?
            Date date = record.getDate();
            if (date.compareTo(lastUpdatedDate) > 0) {
                newRecords.add(record);
                if (date.compareTo(maxDate) > 0)
                    maxDate = date;
            }
        }

        ctx.data.access(FxrRecord.class).saveAll(newRecords);

        // Plot on the fly.
        for (FxrUsage _usage : FxrUsage.values()) {
            for (FxrRecord newRecord : newRecords) {
                Float _rate = newRecord.getRate(_usage);
                if (_rate == null)
                    continue;

                Currency _unit = newRecord.getUnitCurrency();
                IFxrMap fxrMap = getFxrMap(_unit, _usage);

                Date _date = newRecord.getDate();
                fxrMap.plot(_date, _rate);
            }
        }

        lastUpdatedDate = maxDate;
    }

}
