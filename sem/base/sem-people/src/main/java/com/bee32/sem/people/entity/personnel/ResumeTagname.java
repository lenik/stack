package com.bee32.sem.people.entity.personnel;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.NameDict;

/**
 * 员工个人经历的类型
 *
 */
@Entity
public class ResumeTagname extends NameDict {

    private static final long serialVersionUID = 1L;

    public ResumeTagname(String name, String label) {
        super(name, label, "");
    }


    public ResumeTagname(String name, String label, String desc) {
        super(name, label, desc);
    }



    public static final ResumeTagname RESUME = new ResumeTagname("RESUME", "履历");
    public static final ResumeTagname RP = new ResumeTagname("RP", "奖惩", "Rewards and Punishments");
    public static final ResumeTagname EDUCATION = new ResumeTagname("EDUCATION", "教育培训");
}
