package com.bee32.sem.people.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.NameDict;

@Entity
public class PartyLogCategory
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public PartyLogCategory() {
        super();
    }

    public PartyLogCategory(String name, String label) {
        super(name, label);
    }

    public PartyLogCategory(String name, String label, String description) {
        super(name, label, description);
    }

    public static PartyLogCategory NORMALLOG = new PartyLogCategory("NORM", "警告");
    public static PartyLogCategory INSERT = new PartyLogCategory("INSE", "严重警告");
    public static PartyLogCategory DELETE = new PartyLogCategory("DELE", "拘留");
    public static PartyLogCategory UPDATE = new PartyLogCategory("UPDA", "劳改");

}
