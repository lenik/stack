package com.bee32.sem.world.monetary;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.plover.util.i18n.CurrencyOrder;

/**
 * 多币种金额矢量
 *
 * <p lang="en">
 * Multi-Currency Vector
 */
public class MCVector
        implements Serializable, Iterable<MCValue> {

    private static final long serialVersionUID = 1L;

    Map<Currency, BigDecimal> map = new TreeMap<Currency, BigDecimal>(CurrencyOrder.CODE);
    Currency conversion = CurrencyConfig.getNative();

    @Override
    public MCVector clone() {
        MCVector copy = new MCVector();
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

    public void put(MCValue cval) {
        if (cval == null)
            throw new NullPointerException("cval");
        map.put(cval.getCurrency(), cval.getValue());
    }

    public final void put(MCVector vector) {
        for (MCValue cval : vector)
            put(cval);
    }

    public void remove(MCValue cval) {
        if (cval == null)
            throw new NullPointerException("cval");
        map.remove(cval.getCurrency());
    }

    public void remove(Currency currency) {
        if (currency == null)
            throw new NullPointerException("currency");
        map.remove(currency);
    }

    public void add(MCValue cval) {
        if (cval == null)
            throw new NullPointerException("cval");
        BigDecimal _val = map.get(cval.getCurrency());
        if (_val == null) {
            map.put(cval.getCurrency(), cval.getValue());
        } else {
            _val = _val.add(cval.getValue());
            map.put(cval.getCurrency(), _val);
        }
    }

    public void subtract(MCValue cval) {
        if (cval == null)
            throw new NullPointerException("cval");
        BigDecimal _val = map.get(cval.getCurrency());
        if (_val == null) {
            map.put(cval.getCurrency(), cval.getValue().negate());
        } else {
            _val = _val.subtract(cval.getValue());
            map.put(cval.getCurrency(), _val);
        }
    }

    public final void add(MCVector vector) {
        for (MCValue mcv : vector)
            add(mcv);
    }

    public final void subtract(MCVector vector) {
        for (MCValue mcv : vector)
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
        super(entry.getKey(), entry.getValue());
        this.entry = entry;
    }

    @Override
    public void setCurrency(Currency currency) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setValue(BigDecimal value) {
        super.setValue(value);
        entry.setValue(value);
    }

}
