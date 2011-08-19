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

    private final boolean packing;
    private final boolean negative;

    static final Map<String, StockOrderSubject> nameMap = new HashMap<String, StockOrderSubject>();
    static final Map<String, StockOrderSubject> valueMap = new HashMap<String, StockOrderSubject>();

    StockOrderSubject(String value, String name, boolean negative) {
        this(value, name, negative, false);
    }

    StockOrderSubject(String value, String name, boolean negative, boolean packing) {
        super(value, name);
        this.negative = negative;
        this.packing = packing;
    }

    public boolean isNegative() {
        return negative;
    }

    public boolean isPacking() {
        return packing;
    }

    @Override
    protected Map<String, StockOrderSubject> getNameMap() {
        return nameMap;
    }

    @Override
    protected Map<String, StockOrderSubject> getValueMap() {
        return valueMap;
    }

    public static StockOrderSubject forName(String altName) {
        StockOrderSubject subject = nameMap.get(altName);
        if (subject == null)
            throw new NoSuchEnumException(StockOrderSubject.class, altName);
        return subject;
    }

    public static Collection<StockOrderSubject> values() {
        Collection<StockOrderSubject> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    /**
     * @throws NoSuchEnumException
     *             If the value is undefined.
     */
    public static StockOrderSubject valueOf(String value) {
        if (value == null)
            return null;

        StockOrderSubject subject = valueMap.get(value);
        if (subject == null)
            throw new NoSuchEnumException(StockOrderSubject.class, value);

        return subject;
    }

    /** （冗余）结算【物料】，外币已换算 */
    public static final StockOrderSubject PACK_M = new StockOrderSubject("PK_1", "packM", false, true);

    /** （冗余）结算【物料，合成批号】，外币已换算 */
    public static final StockOrderSubject PACK_MB = new StockOrderSubject("PK_2", "packMB", false, true);

    /** （冗余）结算【物料，合成批号，库位】，外币已换算 */
    public static final StockOrderSubject PACK_MBL = new StockOrderSubject("PK_3", "packMBL", false, true);

    /** （冗余）结算【物料】，外币分列 */
    public static final StockOrderSubject PACK_MC = new StockOrderSubject("PKX1", "packMC", false, true);

    /** （冗余）结算【物料，合成批号】，外币分列 */
    public static final StockOrderSubject PACK_MBC = new StockOrderSubject("PKX2", "packMBC", false, true);

    /** （冗余）结算【物料，合成批号，库位】，外币分列 */
    public static final StockOrderSubject PACK_MBLC = new StockOrderSubject("PKX3", "packMBLC", false, true);

    /** 初始化 */
    public static final StockOrderSubject INIT = new StockOrderSubject("INIT", "init", false);

    /** 销售入库 */
    public static final StockOrderSubject TAKE_IN = new StockOrderSubject("TK_I", "takeIn", false);

    /** 销售出库 */
    public static final StockOrderSubject TAKE_OUT = new StockOrderSubject("TK_O", "takeOut", true);

    /** 调拨出库 */
    public static final StockOrderSubject XFER_OUT = new StockOrderSubject("XFRO", "xferOut", true);

    /** 调拨入库 */
    public static final StockOrderSubject XFER_IN = new StockOrderSubject("XFRI", "xferIn", false);

    /** 委外出库 */
    public static final StockOrderSubject OSP_OUT = new StockOrderSubject("OSPO", "ospOut", true);

    /** 委外入库 */
    public static final StockOrderSubject OSP_IN = new StockOrderSubject("OSPI", "ospIn", false);

    /** 盘盈 */
    public static final StockOrderSubject STK_PROFIT = new StockOrderSubject("STKP", "stkProfit", false);

    /** 盘亏 */
    public static final StockOrderSubject STK_SHORTAGE = new StockOrderSubject("STKS", "stkShortage", true);

    /** 计划出库 */
    public static final StockOrderSubject PLAN = new StockOrderSubject("PLAN", "plan", true);

    /** 生产入库 */
    public static final StockOrderSubject PROD_TAKE_IN = new StockOrderSubject("TKIP", "produceTakeIn", false);

    /** 生产出库 */
    public static final StockOrderSubject PROD_TAKE_OUT = new StockOrderSubject("TKOP", "produceTakeOut", true);

}
