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

    public static OrgType LTD_CORP = new OrgType("LTD", "有限责任公司");
    public static OrgType INF_CORP = new OrgType("INF", "无限有限公司");
    public static OrgType FACTORY = new OrgType("FAC", "私营/工厂");
    public static OrgType INDIVIDUAL = new OrgType("IND", "独立人");
    public static OrgType PARTNER = new OrgType("PAR", "私人合伙");
    public static OrgType EDUCATION = new OrgType("EDU", "教育机构");
    public static OrgType MILITARY = new OrgType("MIL", "军事组织");

}
