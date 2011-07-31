package com.bee32.sem.inventory.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.NoSuchEnumException;

/**
 * 库存快照类型
 */
public class StockPeriodType
        extends EnumAlt<Character, StockPeriodType> {

    private static final long serialVersionUID = 1L;

    static final Map<String, StockPeriodType> nameMap = new HashMap<String, StockPeriodType>();
    static final Map<Character, StockPeriodType> valueMap = new HashMap<Character, StockPeriodType>();

    StockPeriodType(char value, String name) {
        super(value, name);
    }

    @Override
    protected Map<String, StockPeriodType> getNameMap() {
        return nameMap;
    }

    @Override
    protected Map<Character, StockPeriodType> getValueMap() {
        return valueMap;
    }

    public static StockPeriodType forName(String altName) {
        StockPeriodType type = nameMap.get(altName);
        if (type == null)
            throw new NoSuchEnumException(StockPeriodType.class, altName);
        return type;
    }

    public static Collection<StockPeriodType> values() {
        Collection<StockPeriodType> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static StockPeriodType valueOf(Character value) {
        if (value == null)
            return null;

        StockPeriodType type = valueMap.get(value);
        if (type == null)
            throw new NoSuchEnumException(StockPeriodType.class, value);

        return type;
    }

    /** 初始库存 */
    public static final StockPeriodType INITIAL = new StockPeriodType('I', "initial");

    /** 常规结转 */
    public static final StockPeriodType REGULAR = new StockPeriodType('r', "regular");

    /** 备份点 */
    public static final StockPeriodType BACKUP = new StockPeriodType('b', "backup");

}
