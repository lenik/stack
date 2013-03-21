package com.bee32.sem.inventory.entity;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;

/**
 * 库存项状态
 */
public class StockItemState
        extends EnumAlt<Integer, StockItemState> {

    private static final long serialVersionUID = 1L;

    final boolean abnormal;

    StockItemState(int value, String name) {
        this(value, name, false);
    }

    StockItemState(int value, String name, boolean abnormal) {
        super(value, name);
        this.abnormal = abnormal;
    }

    public boolean isAbnormal() {
        return abnormal;
    }

    public static StockItemState forName(String name) {
        return forName(StockItemState.class, name);
    }

    public static Collection<StockItemState> values() {
        return values(StockItemState.class);
    }

    public static StockItemState forValue(Integer value) {
        return forValue(StockItemState.class, value);
    }

    public static StockItemState forValue(int value) {
        return forValue(new Integer(value));
    }

    /** 正常 */
    public static final StockItemState NORMAL = new StockItemState('-', "normal", true);

    /** 挂起/等待中，比如入库单中的项目正处于入库中，尚未完成入库的状态。 */
    public static final StockItemState PENDING = new StockItemState('P', "pending");

    /** 盘点中 */
    public static final StockItemState CHECKING = new StockItemState('C', "checking");

    /** 校验中 */
    public static final StockItemState VALIDATING = new StockItemState('V', "validating");

    /** 调拨中 */
    public static final StockItemState TRANSFERRING = new StockItemState('M', "transferring");

    /** 合格 */
    public static final StockItemState QUALIFIED = new StockItemState('Q', "qualified");

    /** 不合格 */
    public static final StockItemState UNQUALIFIED = new StockItemState('U', "unqualified");

    /** 让步(勉强)／勉强合格，让步接收。 */
    public static final StockItemState RELUCTANCE = new StockItemState('R', "reluctance");
}
