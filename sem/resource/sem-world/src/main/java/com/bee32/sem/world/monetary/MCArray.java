package com.bee32.sem.world.monetary;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.free.AbstractNonNullComparator;

public class MCArray
        implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<Currency, BigDecimal> map = new TreeMap<Currency, BigDecimal>(CurrencyCodeComparator.INSTANCE);

    @Override
    public MCArray clone() {
        MCArray copy = new MCArray();
        copy.map.putAll(map);
        return copy;
    }

    public void add(MCValue mcv) {
        if (mcv == null)
            throw new NullPointerException("mcv");
        BigDecimal cval = map.get(mcv.currency);
        if (cval == null) {
            map.put(mcv.currency, mcv.value);
        } else {
            cval = cval.add(mcv.value);
            map.put(mcv.currency, cval);
        }
    }

    public void subtract(MCValue mcv) {
        if (mcv == null)
            throw new NullPointerException("mcv");
        BigDecimal cval = map.get(mcv.currency);
        if (cval == null) {
            map.put(mcv.currency, mcv.value.negate());
        } else {
            cval = cval.subtract(mcv.value);
            map.put(mcv.currency, cval);
        }
    }

    public void multiply(double k) {
        BigDecimal decimal = new BigDecimal(k);
        multiply(decimal);
    }

    public void multiply(BigDecimal k) {
        if (k == null)
            throw new NullPointerException("k");
        for (Entry<Currency, BigDecimal> entry : map.entrySet()) {
            BigDecimal val = entry.getValue();
            val = val.multiply(k);
            entry.setValue(val);
        }
    }

}

class CurrencyCodeComparator
        extends AbstractNonNullComparator<Currency> {

    @Override
    public int compareNonNull(Currency c1, Currency c2) {
        String code1 = c1.getCurrencyCode();
        String code2 = c2.getCurrencyCode();
        int cmp = code1.compareTo(code2);
        return cmp;
    }

    public static final CurrencyCodeComparator INSTANCE = new CurrencyCodeComparator();
}

class CurrencyNumericComparator
        extends AbstractNonNullComparator<Currency> {

    @Override
    public int compareNonNull(Currency c1, Currency c2) {
        int n1 = c1.getNumericCode();
        int n2 = c2.getNumericCode();
        int cmp = n1 - n2;
        return cmp;
    }

    public static final CurrencyNumericComparator INSTANCE = new CurrencyNumericComparator();
}
