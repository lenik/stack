package com.bee32.sem.bom.entity;

import java.math.BigDecimal;
import java.util.HashMap;

public class ConsumptionMap<K>
        extends HashMap<K, BigDecimal> {

    private static final long serialVersionUID = 1L;

    public BigDecimal getConsumption(K key) {
        BigDecimal consumption = get(key);
        if (consumption == null)
            consumption = BigDecimal.ZERO;
        return consumption;
    }

    public void add(K key, BigDecimal add) {
        BigDecimal consumption = get(key);
        if (consumption == null)
            consumption = add;
        else
            consumption = consumption.add(add);
        put(key, consumption);
    }

}
