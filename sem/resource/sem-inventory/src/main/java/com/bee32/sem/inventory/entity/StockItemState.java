package com.bee32.sem.inventory.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.NoSuchEnumException;

/**
 * 项目状态。
 */
public class StockItemState
        extends EnumAlt<Character, StockItemState> {

    private static final long serialVersionUID = 1L;

    static final Map<String, StockItemState> nameMap = new HashMap<String, StockItemState>();
    static final Map<Character, StockItemState> valueMap = new HashMap<Character, StockItemState>();

    StockItemState(char value, String name) {
        super(value, name);
    }

    @Override
    protected Map<String, StockItemState> getNameMap() {
        return nameMap;
    }

    @Override
    protected Map<Character, StockItemState> getValueMap() {
        return valueMap;
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

    public static StockItemState valueOf(String altName) {
        StockItemState state = nameMap.get(altName);
        if (state == null)
            throw new NoSuchEnumException(StockItemState.class, altName);
        return state;
    }

    public static final StockItemState DONE = new StockItemState('-', "done");
    public static final StockItemState PENDING = new StockItemState('P', "pending");
    public static final StockItemState CHECKING = new StockItemState('C', "checking");
    public static final StockItemState VALIDATING = new StockItemState('V', "validating");
    public static final StockItemState MOVING = new StockItemState('M', "moving");

}
