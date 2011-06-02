package com.bee32.sem.chance.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.ShortNameDict;

/**
 * 机会分类
 */
@Entity
public class ChanceCategory
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public ChanceCategory() {
        super();
    }

    public ChanceCategory(String name, String label, String description) {
        super(name, label, description);
    }

    public ChanceCategory(String name, String label) {
        super(name, label);
    }

    public static ChanceCategory IMPORTANT = new ChanceCategory("IMPO", "分类一");

    public static ChanceCategory NORMAL = new ChanceCategory("NORM", "分类二");

    public static ChanceCategory OTHER = new ChanceCategory("OTHE", "分类三");

}
