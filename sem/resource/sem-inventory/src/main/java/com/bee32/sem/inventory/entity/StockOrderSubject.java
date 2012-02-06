package com.bee32.sem.inventory.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bee32.plover.arch.util.EnumAlt;

public final class StockOrderSubject
        extends EnumAlt<String, StockOrderSubject> {

    private static final long serialVersionUID = 1L;

    static final int NEGATIVE = 1;
    static final int VIRTUAL = 2;
    static final int PACKING = 4;
    static final int SPECIAL = 0x1000;

    private final boolean negative;
    private final boolean virtual;
    private final boolean packing;
    private final boolean special;

    static final List<StockOrderSubject> commons = new ArrayList<StockOrderSubject>();
    static final List<StockOrderSubject> virtuals = new ArrayList<StockOrderSubject>();
    static final Set<String> packingSet = new HashSet<String>();
    static final Set<String> virtualSet = new HashSet<String>();
    static final Set<String> virtualOnlySet = new HashSet<String>();
    static final Set<String> commonSet = new HashSet<String>();

    StockOrderSubject(String value, String name, int flags) {
        super(value, name);
        this.negative = (flags & NEGATIVE) != 0;
        this.virtual = (flags & VIRTUAL) != 0;
        this.packing = (flags & PACKING) != 0;
        this.special = (flags & SPECIAL) != 0;

        boolean _special = virtual || packing || special;
        if (!_special) {
            commons.add(this);
            commonSet.add(value);
            virtuals.add(this);
            virtualSet.add(value);
        }

        if (virtual) {
            virtuals.add(this);
            virtualSet.add(value);
            virtualOnlySet.add(value);
        }

        if (packing)
            packingSet.add(value);
    }

    public boolean isNegative() {
        return negative;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public boolean isPacking() {
        return packing;
    }

    public static List<StockOrderSubject> getCommons() {
        return Collections.unmodifiableList(commons);
    }

    public static Set<String> getCommonSet() {
        return Collections.unmodifiableSet(commonSet);
    }

    public static Set<String> getPackingSet() {
        return Collections.unmodifiableSet(packingSet);
    }

    public static List<StockOrderSubject> getVirtuals() {
        return virtuals;
    }

    public static Set<String> getVirtualSet() {
        return Collections.unmodifiableSet(virtualSet);
    }

    public static Set<String> getVirtualOnlySet() {
        return Collections.unmodifiableSet(virtualOnlySet);
    }

    public static StockOrderSubject forName(String name) {
        return forName(StockOrderSubject.class, name);
    }

    public static Collection<StockOrderSubject> values() {
        return values(StockOrderSubject.class);
    }

    public static StockOrderSubject valueOf(String value) {
        return valueOf(StockOrderSubject.class, value);
    }

    /** （冗余）结算【物料】，外币已换算 */
    public static final StockOrderSubject PACK_M /*      */= new StockOrderSubject("PK_1", "packM", PACKING);

    /** （冗余）结算【物料，合成批号】，外币已换算 */
    public static final StockOrderSubject PACK_MB /*     */= new StockOrderSubject("PK_2", "packMB", PACKING);

    /** （冗余）结算【物料，合成批号，库位】，外币已换算 */
    public static final StockOrderSubject PACK_MBL /*    */= new StockOrderSubject("PK_3", "packMBL", PACKING);

    /** （冗余）结算【物料】，外币分列 */
    public static final StockOrderSubject PACK_MC /*     */= new StockOrderSubject("PKX1", "packMC", PACKING);

    /** （冗余）结算【物料，合成批号】，外币分列 */
    public static final StockOrderSubject PACK_MBC /*    */= new StockOrderSubject("PKX2", "packMBC", PACKING);

    /** （冗余）结算【物料，合成批号，库位】，外币分列 */
    public static final StockOrderSubject PACK_MBLC /*   */= new StockOrderSubject("PKX3", "packMBLC", PACKING);

    /** （冗余）缓存（如盘点帐面数量） */
    public static final StockOrderSubject CACHE /* */= new StockOrderSubject("CACH", "cache", SPECIAL);

    /** 初始化 */
    public static final StockOrderSubject INIT /*        */= new StockOrderSubject("INIT", "init", 0);

    /** 采购入库 */
    public static final StockOrderSubject TAKE_IN /*     */= new StockOrderSubject("TK_I", "takeIn", 0);

    /** 销售出库 */
    public static final StockOrderSubject TAKE_OUT /*    */= new StockOrderSubject("TK_O", "takeOut", NEGATIVE);

    /** 调拨出库 */
    public static final StockOrderSubject XFER_OUT /*    */= new StockOrderSubject("XFRO", "xferOut", NEGATIVE);

    /** 调拨入库 */
    public static final StockOrderSubject XFER_IN /*     */= new StockOrderSubject("XFRI", "xferIn", 0);

    /** 委外出库 */
    public static final StockOrderSubject OSP_OUT /*     */= new StockOrderSubject("OSPO", "ospOut", NEGATIVE);

    /** 委外入库 */
    public static final StockOrderSubject OSP_IN /*      */= new StockOrderSubject("OSPI", "ospIn", 0);

    /** 盘盈/盘亏 （仅差值） */
    public static final StockOrderSubject STKD /*  */= new StockOrderSubject("STKD", "stkDiff", 0);

    /** 物料报损 */
    public static final StockOrderSubject DAMAGED /*     */= new StockOrderSubject("DAMG", "damaged", NEGATIVE);

    /** 计划出库 */
    public static final StockOrderSubject PLAN_OUT /*    */= new StockOrderSubject("PLAN", "planOut", VIRTUAL | NEGATIVE);

    /** 生产入库 */
    public static final StockOrderSubject FACTORY_IN /*  */= new StockOrderSubject("TKFI", "factoryIn", 0);

    /** 生产出库 */
    public static final StockOrderSubject FACTORY_OUT /* */= new StockOrderSubject("TKFO", "factoryOut", NEGATIVE);

}
