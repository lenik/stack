package com.bee32.sem.world.monetary;

import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.sem.misc.EntityCriteria;
import com.bee32.sem.misc.LocalDateUtil;
import com.bee32.sem.world.math.InterpolatedMap;

public class FxrMap {

    static Logger logger = LoggerFactory.getLogger(FxrMap.class);

    final Currency unit;
    final FxrUsage usage;

    InterpolatedMap imap;

    Set<Integer> loadedMonths = new TreeSet<Integer>();

    public FxrMap(Currency unit, FxrUsage usage) {
        if (unit == null)
            throw new NullPointerException("unit");
        if (usage == null)
            throw new NullPointerException("usage");
        this.unit = unit;
        this.usage = usage;
    }

    public synchronized void lazyLoad(Date from, Date to) {
        int x1 = LocalDateUtil.monthIndex(from) - 1;
        int x2 = LocalDateUtil.monthIndex(to) + 1;
        for (int x = x1; x <= x2; x++) {
            if (loadedMonths.add(x)) {
                Date _from = LocalDateUtil.beginOfMonthIndex(x);
                Date _to = LocalDateUtil.endOfMonthIndex(x);
                load(_from, _to);
            }
        }
    }

    void load(Date from, Date to) {
        // String s1 = Dates.dateFormat.format(from);
        // String s2 = Dates.dateFormat.format(to);
        // logger.debug("Load " + s1 + " .. " + s2);

        List<FxrRecord> records = dataManager.asFor(FxrRecord.class).list(//
                EntityCriteria.betweenEx("date", from, to), //
                MonetaryCriteria.unitCurrencyOf(unit));

        for (FxrRecord record : records) {
            Date date = record.getDate();
            int dayx = LocalDateUtil.dayIndex(date);

            Float rate = record.getRate(usage);
            if (rate == null)
                // Some rates for specific usage may be optional.
                continue;

            imap.put(dayx, rate.doubleValue());
        }
    }

    CommonDataManager dataManager;

    public void setDataManager(CommonDataManager dataManager) {
        if (dataManager == null)
            throw new NullPointerException("dataManager");
        this.dataManager = dataManager;
    }

}
