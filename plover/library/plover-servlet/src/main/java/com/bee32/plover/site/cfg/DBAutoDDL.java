package com.bee32.plover.site.cfg;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.ILabelledEntry;
import com.bee32.plover.arch.util.NoSuchEnumException;

public class DBAutoDDL
        extends SiteConfigEnum<String, DBAutoDDL>
        implements ILabelledEntry {

    private static final long serialVersionUID = 1L;

    private DBAutoDDL(String hibernatePropertyValue, String name) {
        super(hibernatePropertyValue, name);
    }

    public String getHibernatePropertyValue() {
        return getValue();
    }

    @Override
    public String getEntryLabel() {
        return label;
    }

    static final Map<String, DBAutoDDL> nameMap = new HashMap<String, DBAutoDDL>();
    static final Map<String, DBAutoDDL> valueMap = new HashMap<String, DBAutoDDL>();

    public static Collection<DBAutoDDL> values() {
        Collection<DBAutoDDL> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static DBAutoDDL forName(String altName) {
        DBAutoDDL autoddl = nameMap.get(altName);
        if (autoddl == null)
            throw new NoSuchEnumException(DBAutoDDL.class, altName);
        return autoddl;
    }

    public static DBAutoDDL forValue(String value) {
        if (value == null)
            return null;

        DBAutoDDL autoddl = valueMap.get(value);
        if (autoddl == null)
            throw new NoSuchEnumException(DBAutoDDL.class, value);

        return autoddl;
    }

    /** 仅自动创建 */
    public static final DBAutoDDL Create = new DBAutoDDL("create", "create");

    /** 自动更新（仅自动添加新的表和字段） */
    public static final DBAutoDDL Update = new DBAutoDDL("update", "update");

    /** 仅校验（更安全，但需要手动更新） */
    public static final DBAutoDDL Validate = new DBAutoDDL("validate", "validate");

    /** 自动创建和删除（危险！） */
    public static final DBAutoDDL CreateDrop = new DBAutoDDL("create-drop", "create-drop");

}