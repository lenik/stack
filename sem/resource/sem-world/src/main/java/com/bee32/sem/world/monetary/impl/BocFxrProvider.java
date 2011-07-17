package com.bee32.sem.world.monetary.impl;

import java.util.Currency;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.world.monetary.AbstractFxrProvider;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.FxrTable;

/**
 * 中国银行提供的外汇查询。
 */
public class BocFxrProvider
        extends AbstractFxrProvider {

    static Logger logger = LoggerFactory.getLogger(BocFxrProvider.class);

    Map<String, Double> cache;
    long updatedDate;

    @Override
    public synchronized Double lookup(Currency code) {
        boolean isExpired = false;
        if (cache == null)
            isExpired = true;
        else if (System.currentTimeMillis() - updatedDate > 3600000)
            isExpired = true;

        if (isExpired) {
            try {
                cache = buildCache();
            } catch (Exception e) {
                logger.warn("Can't build the rate table: " + e.getMessage(), e);
                return null;
            }
            assert cache != null;
            updatedDate = System.currentTimeMillis();
        }

        return cache.get(code); // To do
    }

    @Override
    public FxrTable getLatestFxrTable()
            throws FxrQueryException {
        return null;
    }

}
