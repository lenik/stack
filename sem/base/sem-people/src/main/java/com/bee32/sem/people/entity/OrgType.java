package com.bee32.sem.people.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.ShortNameDict;

@Entity
public class OrgType
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public static OrgType PARTNER;
    public static OrgType COMPANY;
    public static OrgType EDU;
    public static OrgType ARMY;

}
