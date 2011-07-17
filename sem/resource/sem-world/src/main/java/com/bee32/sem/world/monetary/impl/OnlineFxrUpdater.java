package com.bee32.sem.world.monetary.impl;

import java.io.IOException;
import java.util.Currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.EnterpriseService;
import com.bee32.sem.world.monetary.CurrencyConfig;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.FxrTable;
import com.bee32.sem.world.monetary.IFxrUpdater;

public abstract class OnlineFxrUpdater
        extends EnterpriseService
        implements IFxrUpdater {

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

        Currency unitCurrency = table.getUnitCurrency();
        if (unitCurrency != CurrencyConfig.DEFAULT) {
            logger.error("FXR Update is skipped because of bad unit currency in FXR table: " + unitCurrency);
            return;
        }

        commit(table);
    }

    @Transactional(readOnly = false)
    void commit(FxrTable table) {

    }

}
