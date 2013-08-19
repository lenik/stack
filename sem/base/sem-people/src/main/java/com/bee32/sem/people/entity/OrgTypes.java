package com.bee32.sem.people.entity;

import com.bee32.plover.orm.sample.StandardSamples;

/**
 * 组织机构类型
 *
 * <p lang="en">
 * Organization Types
 */
public class OrgTypes
        extends StandardSamples {

    public final OrgType LTD_CORP = new OrgType("LTD", "有限责任公司");
    public final OrgType INF_CORP = new OrgType("INF", "无限有限公司");
    public final OrgType FACTORY = new OrgType("FAC", "私营/工厂");
    public final OrgType INDIVIDUAL = new OrgType("IND", "独立人");
    public final OrgType PARTNER = new OrgType("PAR", "私人合伙");
    public final OrgType EDUCATION = new OrgType("EDU", "教育机构");
    public final OrgType MILITARY = new OrgType("MIL", "军事组织");

}
