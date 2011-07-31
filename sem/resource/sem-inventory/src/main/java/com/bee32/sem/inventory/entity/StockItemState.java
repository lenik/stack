package com.bee32.sem.inventory.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.NoSuchEnumException;

/**
 * 库存项状态。
 */
public class StockItemState
        extends EnumAlt<Character, StockItemState> {

    private static final long serialVersionUID = 1L;

    static final Map<String, StockItemState> nameMap = new HashMap<String, StockItemState>();
    static final Map<Character, StockItemState> valueMap = new HashMap<Character, StockItemState>();

    final boolean abnormal;

    StockItemState(char value, String name) {
        this(value, name, false);
    }

    StockItemState(char value, String name, boolean abnormal) {
        super(value, name);
        this.abnormal = abnormal;
    }

    public boolean isAbnormal() {
        return abnormal;
    }

    @Override
    protected Map<String, StockItemState> getNameMap() {
        return nameMap;
    }

    @Override
    protected Map<Character, StockItemState> getValueMap() {
        return valueMap;
    }

    public static StockItemState forName(String altName) {
        StockItemState state = nameMap.get(altName);
        if (state == null)
            throw new NoSuchEnumException(StockItemState.class, altName);
        return state;
    }

    public static Collection<StockItemState> values() {
        Collection<StockItemState> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static StockItemState valueOf(char value) {
        return valueOf(new Character(value));
    }

    public static StockItemState valueOf(Character value) {
        if (value == null)
            return null;

        StockItemState state = valueMap.get(value);
        if (state == null)
            throw new NoSuchEnumException(StockItemState.class, value);

        return state;
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

}
