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
public class InventorySnapshotType
        extends EnumAlt<Character, InventorySnapshotType> {

    private static final long serialVersionUID = 1L;

    static final Map<String, InventorySnapshotType> nameMap = new HashMap<String, InventorySnapshotType>();
    static final Map<Character, InventorySnapshotType> valueMap = new HashMap<Character, InventorySnapshotType>();

    InventorySnapshotType(char value, String name) {
        super(value, name);
    }

    @Override
    protected Map<String, InventorySnapshotType> getNameMap() {
        return nameMap;
    }

    @Override
    protected Map<Character, InventorySnapshotType> getValueMap() {
        return valueMap;
    }

    public static Collection<InventorySnapshotType> values() {
        Collection<InventorySnapshotType> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static InventorySnapshotType valueOf(Character value) {
        if (value == null)
            return null;

        InventorySnapshotType type = valueMap.get(value);
        if (type == null)
            throw new NoSuchEnumException(InventorySnapshotType.class, value);

        return type;
    }

    public static InventorySnapshotType valueOf(String altName) {
        InventorySnapshotType type = nameMap.get(altName);
        if (type == null)
            throw new NoSuchEnumException(InventorySnapshotType.class, altName);
        return type;
    }

    /** 初始库存 */
    public static final InventorySnapshotType INITIAL = new InventorySnapshotType('I', "initial");

    /** 常规结转 */
    public static final InventorySnapshotType REGULAR = new InventorySnapshotType('r', "regular");

    /** 备份点 */
    public static final InventorySnapshotType BACKUP = new InventorySnapshotType('b', "backup");

    /** 工作副本 */
    public static final InventorySnapshotType WORKCOPY = new InventorySnapshotType('w', "workcopy");

}
