package com.bee32.sem.inventory.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.NoSuchEnumException;

public class StockOrderSubject
        extends EnumAlt<String, StockOrderSubject> {

    private static final long serialVersionUID = 1L;

    static final Map<String, StockOrderSubject> nameMap = new HashMap<String, StockOrderSubject>();
    static final Map<String, StockOrderSubject> valueMap = new HashMap<String, StockOrderSubject>();

    StockOrderSubject(String value, String name) {
        super(value, name);
    }

    @Override
    protected Map<String, StockOrderSubject> getNameMap() {
        return nameMap;
    }

    @Override
    protected Map<String, StockOrderSubject> getValueMap() {
        return valueMap;
    }

    public static Collection<StockOrderSubject> values() {
        Collection<StockOrderSubject> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static StockOrderSubject valueOf(String value) {
        if (value == null)
            return null;

        StockOrderSubject subject = valueMap.get(value);
        if (subject == null)
            throw new NoSuchEnumException(StockOrderSubject.class, value);

        return subject;
    }

    public static StockOrderSubject get(String altName) {
        StockOrderSubject subject = nameMap.get(altName);
        if (subject == null)
            throw new NoSuchEnumException(StockOrderSubject.class, altName);
        return subject;
    }

    public static final StockOrderSubject INIT = new StockOrderSubject("INI", "init");
    public static final StockOrderSubject RESUME = new StockOrderSubject("RSM", "resume");

    public static final StockOrderSubject CHECKIN = new StockOrderSubject("NCI", "checkin");
    public static final StockOrderSubject CHECKOUT = new StockOrderSubject("NCO", "checkout");
    public static final StockOrderSubject F_CHECKIN = new StockOrderSubject("FCI", "f_checkin");
    public static final StockOrderSubject F_CHECKOUT = new StockOrderSubject("FCO", "f_checkout");

    public static final StockOrderSubject STOCKTAKING = new StockOrderSubject("STK", "stocktaking");
    public static final StockOrderSubject MOVE = new StockOrderSubject("MOV", "move");

}
