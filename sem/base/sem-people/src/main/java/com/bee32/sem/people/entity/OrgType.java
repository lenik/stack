package com.bee32.sem.people.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.ShortNameDict;

@Entity
public class OrgType
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public OrgType() {
        super();
    }

    public OrgType(String name, String label, String description) {
        super(name, label, description);
    }

    public OrgType(String name, String label) {
        super(name, label);
    }

    public static OrgType PARTNER = new OrgType("PART", "合作伙伴");

    public static OrgType COMPANY = new OrgType("COMP", "公司");

    public static OrgType EDU = new OrgType("EDUC", "教育");

    public static OrgType ARMY = new OrgType("ARMY", "军队");

}
