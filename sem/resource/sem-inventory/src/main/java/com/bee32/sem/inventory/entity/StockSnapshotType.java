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
public class StockSnapshotType
        extends EnumAlt<Character, StockSnapshotType> {

    private static final long serialVersionUID = 1L;

    static final Map<String, StockSnapshotType> nameMap = new HashMap<String, StockSnapshotType>();
    static final Map<Character, StockSnapshotType> valueMap = new HashMap<Character, StockSnapshotType>();

    StockSnapshotType(char value, String name) {
        super(value, name);
    }

    @Override
    protected Map<String, StockSnapshotType> getNameMap() {
        return nameMap;
    }

    @Override
    protected Map<Character, StockSnapshotType> getValueMap() {
        return valueMap;
    }

    public static Collection<StockSnapshotType> values() {
        Collection<StockSnapshotType> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static StockSnapshotType valueOf(Character value) {
        if (value == null)
            return null;

        StockSnapshotType type = valueMap.get(value);
        if (type == null)
            throw new NoSuchEnumException(StockSnapshotType.class, value);

        return type;
    }

    public static StockSnapshotType valueOf(String altName) {
        StockSnapshotType type = nameMap.get(altName);
        if (type == null)
            throw new NoSuchEnumException(StockSnapshotType.class, altName);
        return type;
    }

    /** 初始库存 */
    public static final StockSnapshotType INITIAL = new StockSnapshotType('I', "initial");

    /** 常规结转 */
    public static final StockSnapshotType REGULAR = new StockSnapshotType('r', "regular");

    /** 备份点 */
    public static final StockSnapshotType BACKUP = new StockSnapshotType('b', "backup");

}
