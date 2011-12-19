package com.bee32.sem.people.entity.personnel;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.NameDict;

/**
 * 学历字典类
 *
 */
@Entity
public class EducationTagname extends NameDict {

    private static final long serialVersionUID = 1L;

    public EducationTagname(String name, String label) {
        super(name, label, "");
    }


    public EducationTagname(String name, String label, String desc) {
        super(name, label, desc);
    }

    public static final ResumeTagname L1 = new ResumeTagname("L1", "小学");
    public static final ResumeTagname L2 = new ResumeTagname("L2", "中学");
    public static final ResumeTagname L3 = new ResumeTagname("L3", "高中");
    public static final ResumeTagname L4 = new ResumeTagname("L4", "中专");
    public static final ResumeTagname L5 = new ResumeTagname("L5", "大专");
    public static final ResumeTagname L6 = new ResumeTagname("L6", "本科");

}
