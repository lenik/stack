package com.bee32.sem.inventory.entity;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;

/**
 * 库存快照类型
 */
public class StockPeriodType
        extends EnumAlt<Character, StockPeriodType> {

    private static final long serialVersionUID = 1L;

    StockPeriodType(char value, String name) {
        super(value, name);
    }

    public static StockPeriodType forName(String name) {
        return forName(StockPeriodType.class, name);
    }

    public static Collection<StockPeriodType> values() {
        return values(StockPeriodType.class);
    }

    public static StockPeriodType forValue(Character value) {
        return forValue(StockPeriodType.class, value);
    }

    public static StockPeriodType forValue(char value) {
        return forValue(new Character(value));
    }

    /** 初始库存 */
    public static final StockPeriodType INITIAL = new StockPeriodType('I', "initial");

    /** 常规结转 */
    public static final StockPeriodType REGULAR = new StockPeriodType('r', "regular");

    /** 备份点 */
    public static final StockPeriodType BACKUP = new StockPeriodType('b', "backup");

}
