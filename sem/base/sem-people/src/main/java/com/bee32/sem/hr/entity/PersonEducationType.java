package com.bee32.sem.hr.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.NameDict;

/**
 * 学历字典类
 *
 */
@Entity
public class PersonEducationType
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public PersonEducationType() {
        super();
    }

    public PersonEducationType(String name, String label) {
        super(name, label);
    }

    public PersonEducationType(String name, String label, String desc) {
        super(name, label, desc);
    }

//    public static final PersonEducationType L1 = new PersonEducationType("L1", "小学");
//    public static final PersonEducationType L2 = new PersonEducationType("L2", "中学");
//    public static final PersonEducationType L3 = new PersonEducationType("L3", "高中");
//    public static final PersonEducationType L4 = new PersonEducationType("L4", "中专");
//    public static final PersonEducationType L5 = new PersonEducationType("L5", "大专");
//    public static final PersonEducationType L6 = new PersonEducationType("L6", "本科");

}
