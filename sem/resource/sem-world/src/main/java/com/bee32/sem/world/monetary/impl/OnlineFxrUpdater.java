package com.bee32.sem.world.monetary.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.EnterpriseService;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.FxrRecord;
import com.bee32.sem.world.monetary.FxrTable;
import com.bee32.sem.world.monetary.ICurrencyAware;
import com.bee32.sem.world.monetary.IFxrUpdater;

public abstract class OnlineFxrUpdater
        extends EnterpriseService
        implements IFxrUpdater, ICurrencyAware {

    static Logger logger = LoggerFactory.getLogger(OnlineFxrUpdater.class);

    static final int INTERVAL_DEBUG = 1;
    static final int INTERVAL_PRODUCT = 12 * 60;

    @Override
    public int getPreferredInterval() {
        return INTERVAL_DEBUG;
    }

    /**
     * 获取最新的汇率表。
     */
    protected abstract FxrTable download()
            throws IOException;

    @Override
    public void update()
            throws FxrQueryException {

        FxrTable table;
        try {
            table = download();
        } catch (IOException e) {
            logger.error("Failed to download FXR", e);
            return;
        }

        Currency quoteCurrency = table.getQuoteCurrency();
        if (quoteCurrency != NATIVE_CURRENCY) {
            logger.error("FXR Update is skipped because of bad quote currency in FXR table: " + quoteCurrency);
            return;
        }

        commit(table);
    }

    static Date lastUpdatedDate;

    @Transactional(readOnly = false)
    synchronized void commit(FxrTable table) {

        if (lastUpdatedDate == null) {
            FxrRecord mostRecentRecord = asFor(FxrRecord.class).getFirst(Order.desc("date"));
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

        asFor(FxrRecord.class).saveAll(newRecords);
        lastUpdatedDate = maxDate;
    }

}
