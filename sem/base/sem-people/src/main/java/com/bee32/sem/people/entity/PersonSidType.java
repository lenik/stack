package com.bee32.sem.people.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.ShortNameDict;

@Entity
public class PersonSidType
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public PersonSidType() {
        super();
    }

    public PersonSidType(String name, String label, String description) {
        super(name, label, description);
    }

    public PersonSidType(String name, String label) {
        super(name, label);
    }

    public static PersonSidType IDENTITYCARD = new PersonSidType("I.D.", "身份证", "天朝基本证件");
    public static PersonSidType PASSPORT = new PersonSidType("PASS", "护照");
    public static PersonSidType DRIVINGLICENES = new PersonSidType("DRIV", "驾驶证");
    public static PersonSidType STUDENT = new PersonSidType("STUD", "学生证");

}
