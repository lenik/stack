package com.bee32.sem.people.entity;

import com.bee32.plover.orm.util.StandardSamples;

public class PartyRecordCategories
        extends StandardSamples {

    public final PartyRecordCategory INFO = new PartyRecordCategory("INFO", "信息");
    public final PartyRecordCategory ACTIVITY = new PartyRecordCategory("ACT", "行动记要");

    // 以下并入奖惩。
    // public final PartyRecordCategory WARNING = new PartyRecordCategory("WARN", "警告处分");
    // public final PartyRecordCategory SEVERE = new PartyRecordCategory("SEVE", "严重警告处分");
    // public final PartyRecordCategory CUSTODY = new PartyRecordCategory("CSTO", "行政拘留");
    // public final PartyRecordCategory JAIL = new PartyRecordCategory("JAIL", "监禁");

    public final PartyRecordCategory REWARDS = new PartyRecordCategory("R/P", "奖惩情况");
    public final PartyRecordCategory EDUCATION = new PartyRecordCategory("EDU", "教育经历");
    public final PartyRecordCategory EMPLOYMENT = new PartyRecordCategory("EMP", "工作经验");

}
