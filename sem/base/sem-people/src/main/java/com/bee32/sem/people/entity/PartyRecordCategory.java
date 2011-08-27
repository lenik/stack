package com.bee32.sem.people.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.NameDict;

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

    public static PartyRecordCategory INFO = new PartyRecordCategory("INFO", "信息");
    public static PartyRecordCategory ACTIVITY = new PartyRecordCategory("ACTI", "行动记要");
    public static PartyRecordCategory WARNING = new PartyRecordCategory("WARN", "警告处分");
    public static PartyRecordCategory SEVERE = new PartyRecordCategory("SEVE", "严重警告处分");
    public static PartyRecordCategory CUSTODY = new PartyRecordCategory("CSTO", "行政拘留");
    public static PartyRecordCategory JAIL = new PartyRecordCategory("JAIL", "监禁");

}
