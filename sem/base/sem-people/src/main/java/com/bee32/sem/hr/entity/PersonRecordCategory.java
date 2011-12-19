package com.bee32.sem.hr.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.NameDict;

@Entity
public class PersonRecordCategory
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public PersonRecordCategory() {
        super();
    }

    public PersonRecordCategory(String name, String label) {
        super(name, label);
    }

    public PersonRecordCategory(String name, String label, String description) {
        super(name, label, description);
    }

    public static PersonRecordCategory INFO = new PersonRecordCategory("INFO", "信息");
    public static PersonRecordCategory ACTIVITY = new PersonRecordCategory("ACT", "行动记要");

    // 以下并入奖惩。
    // public static PartyRecordCategory WARNING = new PartyRecordCategory("WARN", "警告处分");
    // public static PartyRecordCategory SEVERE = new PartyRecordCategory("SEVE", "严重警告处分");
    // public static PartyRecordCategory CUSTODY = new PartyRecordCategory("CSTO", "行政拘留");
    // public static PartyRecordCategory JAIL = new PartyRecordCategory("JAIL", "监禁");

    public static PersonRecordCategory INTRO = new PersonRecordCategory("INTR", "个人简历");
    public static PersonRecordCategory REWARDS = new PersonRecordCategory("R/P", "奖惩情况");
    public static PersonRecordCategory EDUCATION = new PersonRecordCategory("EDU", "教育经历");
    public static PersonRecordCategory EMPLOYMENT = new PersonRecordCategory("EMP", "工作经验");

}
