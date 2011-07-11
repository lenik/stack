package com.bee32.sem.inventory.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.NoSuchEnumException;

public final class StockOrderSubject
        extends EnumAlt<String, StockOrderSubject> {

    private static final long serialVersionUID = 1L;

    private final boolean negative;

    static final Map<String, StockOrderSubject> nameMap = new HashMap<String, StockOrderSubject>();
    static final Map<String, StockOrderSubject> valueMap = new HashMap<String, StockOrderSubject>();

    StockOrderSubject(String value, String name, boolean negative) {
        super(value, name);
        this.negative = negative;
    }

    public boolean isNegative() {
        return negative;
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

    /** 初值/期初结余 */
    public static final StockOrderSubject START = new StockOrderSubject("STA", "start", false);

    /** 入库 */
    public static final StockOrderSubject TAKE_IN = new StockOrderSubject("TKI", "takeIn", false);
    /** 出库 */
    public static final StockOrderSubject TAKE_OUT = new StockOrderSubject("TKO", "takeOut", true);

    /** 调拨出库 */
    public static final StockOrderSubject XFER_OUT = new StockOrderSubject("TXO", "xferOut", true);
    /** 调拨入库 */
    public static final StockOrderSubject XFER_IN = new StockOrderSubject("TXI", "xferIn", false);

    /** 委外出库 */
    public static final StockOrderSubject OSP_OUT = new StockOrderSubject("OPO", "ospOut", true);
    /** 委外入库 */
    public static final StockOrderSubject OSP_IN = new StockOrderSubject("OPI", "ospIn", false);

    /** 盘盈 */
    public static final StockOrderSubject STK_PROFIT = new StockOrderSubject("STP", "stkProfit", false);
    /** 盘亏 */
    public static final StockOrderSubject STK_SHORTAGE = new StockOrderSubject("STS", "stkShortage", true);

}
