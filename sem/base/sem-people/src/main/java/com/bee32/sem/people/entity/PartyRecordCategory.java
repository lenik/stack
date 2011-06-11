package com.bee32.sem.people.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.NameDict;

@Entity
public class PartyRecordCategory
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public PartyRecordCategory() {
        super();
    }

    public PartyRecordCategory(String name, String label) {
        super(name, label);
    }

    public PartyRecordCategory(String name, String label, String description) {
        super(name, label, description);
    }

    public static PartyRecordCategory NORMALLOG = new PartyRecordCategory("NORM", "警告");
    public static PartyRecordCategory INSERT = new PartyRecordCategory("INSE", "严重警告");
    public static PartyRecordCategory DELETE = new PartyRecordCategory("DELE", "拘留");
    public static PartyRecordCategory UPDATE = new PartyRecordCategory("UPDA", "劳改");

}
