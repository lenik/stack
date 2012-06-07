package com.bee32.plover.site.cfg;

import java.util.Collection;

import com.bee32.plover.arch.util.ILabelledEntry;

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

    public static Collection<DBAutoDDL> values() {
        return values(DBAutoDDL.class);
    }

    public static DBAutoDDL forName(String altName) {
        return forName(DBAutoDDL.class, altName);
    }

    public static DBAutoDDL forValue(String value) {
        return forValue(DBAutoDDL.class, value);
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