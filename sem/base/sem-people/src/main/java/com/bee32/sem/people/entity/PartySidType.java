package com.bee32.sem.people.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.ShortNameDict;

@Entity
public class PartySidType
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public static final char PERSON = 'P';
    public static final char ORG = 'O';

    char category;

    public PartySidType() {
        super();
    }

    public PartySidType(char category, String name, String label, String description) {
        super(name, label, description);
        this.category = category;
    }

    @Column(nullable = false)
    public char getCategory() {
        return category;
    }

    public void setCategory(char category) {
        this.category = category;
    }

    public static PartySidType IDENTITYCARD = new PartySidType(PERSON, "I.D.", "身份证", "公民身份证件");
    public static PartySidType PASSPORT = new PartySidType(PERSON, "PASS", "护照", "出国护照");
    public static PartySidType DRIVINGLICENES = new PartySidType(PERSON, "DRIV", "驾驶证", "机动车驾驶证件");
    public static PartySidType STUDENT = new PartySidType(PERSON, "STUD", "学生证", "在校学生证明");

    public static PartySidType TAX_ID = new PartySidType(ORG, "TAX", "税务识别号", "企业税务识别号");

}
