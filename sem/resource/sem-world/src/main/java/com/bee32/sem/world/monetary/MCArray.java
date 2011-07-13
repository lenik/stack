package com.bee32.sem.world.monetary;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.free.AbstractNonNullComparator;

public class MCArray
        implements Serializable, ICurrencyAware, Iterable<MCValue> {

    private static final long serialVersionUID = 1L;

    Map<Currency, BigDecimal> map = new TreeMap<Currency, BigDecimal>(CurrencyCodeComparator.INSTANCE);
    Currency conversion = DEFAULT_CURRENCY;

    @Override
    public MCArray clone() {
        MCArray copy = new MCArray();
        copy.map.putAll(map);
        return copy;
    }

    static class Iter
            implements Iterator<MCValue> {

        Iterator<Entry<Currency, BigDecimal>> entryIter;

        public Iter(Iterator<Entry<Currency, BigDecimal>> entryIter) {
            if (entryIter == null)
                throw new NullPointerException("entryIter");
            this.entryIter = entryIter;
        }

        @Override
        public boolean hasNext() {
            return entryIter.hasNext();
        }

        @Override
        public MCValue next() {
            Entry<Currency, BigDecimal> entry = entryIter.next();
            return new VirtualMCV(entry);
        }

        @Override
        public void remove() {
            entryIter.remove();
        }

    }

    @Override
    public Iterator<MCValue> iterator() {
        return new Iter(map.entrySet().iterator());
    }

    /**
     * Get the currency used for conversion.
     *
     * When
     *
     * @return <code>null</code> if conversion id disabled.
     */
    public Currency getConversion() {
        return conversion;
    }

    /**
     * @param conversion
     *            The currency used for conversion.
     */
    public void setConversion(Currency conversion) {
        this.conversion = conversion;
    }

    public void add(MCValue mcv) {
        if (mcv == null)
            throw new NullPointerException("mcv");
        BigDecimal cval = map.get(mcv.getCurrency());
        if (cval == null) {
            map.put(mcv.getCurrency(), mcv.getValue());
        } else {
            cval = cval.add(mcv.getValue());
            map.put(mcv.getCurrency(), cval);
        }
    }

    public void subtract(MCValue mcv) {
        if (mcv == null)
            throw new NullPointerException("mcv");
        BigDecimal cval = map.get(mcv.getCurrency());
        if (cval == null) {
            map.put(mcv.getCurrency(), mcv.getValue().negate());
        } else {
            cval = cval.subtract(mcv.getValue());
            map.put(mcv.getCurrency(), cval);
        }
    }

    public final void add(MCArray mca) {
        for (MCValue mcv : mca)
            add(mcv);
    }

    public final void subtract(MCArray mca) {
        for (MCValue mcv : mca)
            subtract(mcv);
    }

    public void multiply(int k) {
        BigDecimal decimal = new BigDecimal(k);
        multiply(decimal);
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

class VirtualMCV
        extends MCValue {

    private static final long serialVersionUID = 1L;

    final Entry<Currency, BigDecimal> entry;

    public VirtualMCV(Entry<Currency, BigDecimal> entry) {
        if (entry == null)
            throw new NullPointerException("entry");
        this.entry = entry;
    }

    @Override
    public Currency getCurrency() {
        return entry.getKey();
    }

    @Override
    public void setCurrency(Currency currency) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BigDecimal getValue() {
        return entry.getValue();
    }

    @Override
    public void setValue(BigDecimal value) {
        entry.setValue(value);
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
