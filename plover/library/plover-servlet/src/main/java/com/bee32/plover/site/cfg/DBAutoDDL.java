package com.bee32.plover.site.cfg;

import com.bee32.plover.arch.util.ILabelledEntry;

public enum DBAutoDDL
        implements ILabelledEntry {

    Create("create", "仅自动创建"),

    Update("update", "自动更新（仅自动添加新的表和字段）"),

    Validate("validate", "仅校验（更安全，但需要手动更新）"),

    CreateDrop("create-drop", "自动创建和删除（危险！）"),

    ;

    final String hibernatePropertyValue;
    final String label;

    private DBAutoDDL(String hibernatePropertyValue, String label) {
        this.hibernatePropertyValue = hibernatePropertyValue;
        this.label = label;
    }

    public String getHibernatePropertyValue() {
        return hibernatePropertyValue;
    }

    @Override
    public String getEntryLabel() {
        return label;
    }

}
