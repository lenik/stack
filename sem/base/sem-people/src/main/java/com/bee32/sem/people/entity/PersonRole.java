package com.bee32.sem.people.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang.StringUtils;

import com.bee32.plover.ox1.c.CEntityAuto;
import com.bee32.plover.ox1.color.Yellow;

/**
 * 人员角色
 *
 * 自然人在公司中的角色。
 *
 * <p lang="en">
 * Person Role In Organization Unit
 */
@Entity
@Yellow
@SequenceGenerator(name = "idgen", sequenceName = "person_role_seq", allocationSize = 1)
public class PersonRole
        extends CEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    public static final int ALT_ORG_UNIT_LENGTH = 100;
    public static final int ROLE_LENGTH = 30;
    public static final int ROLE_DETAIL_LENGTH = 50;
    public static final int DESCRIPTION_LENGTH = 200;

    Person person;
    Org org;
    OrgUnit orgUnit;
    String altOrgUnit;
    String role;
    String roleDetail;
    String description;

    @Override
    public void populate(Object source) {
        if (source instanceof PersonRole)
            _populate((PersonRole) source);
        else
            super.populate(source);
    }

    protected void _populate(PersonRole o) {
        super._populate(o);
        person = o.person;
        org = o.org;
        orgUnit = o.orgUnit;
        altOrgUnit = o.altOrgUnit;
        role = o.role;
        roleDetail = o.roleDetail;
        description = o.description;
    }

    /**
     * 人员
     *
     * 相关的人员。
     */
    @ManyToOne(optional = false)
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        if (person == null)
            throw new NullPointerException("person");
        this.person = person;
    }

    /**
     * 组织
     *
     * 相关的组织。
     */
    @ManyToOne(optional = false)
    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        if (org == null)
            throw new NullPointerException("org");
        this.org = org;
    }

    /**
     * 所在部门
     *
     * 关联组织中的相关部门。
     */
    @ManyToOne
    public OrgUnit getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnit orgUnit) {
        this.orgUnit = orgUnit;
    }

    /**
     * 所在部门名称
     *
     * （过渡）（临时使用的）关联组织中的部门名称。
     */
    @Deprecated
    @Column(length = ALT_ORG_UNIT_LENGTH)
    public String getAltOrgUnit() {
        return altOrgUnit;
    }

    @Deprecated
    public void setAltOrgUnit(String altOrgUnit) {
        this.altOrgUnit = altOrgUnit;
    }

    /**
     * 职务
     *
     * 相关人员在组织部门中担任的职务。
     */
    @Column(length = ROLE_LENGTH)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * 业务说明
     *
     * 相关人员在组织部门中负责的具体业务说明。
     */
    // @Basic(optional = false)
    @Column(length = ROLE_DETAIL_LENGTH)
    public String getRoleDetail() {
        return roleDetail;
    }

    public void setRoleDetail(String roleDetail) {
        this.roleDetail = roleDetail;
    }

    /**
     * 备注
     *
     * 附加的备注信息。
     */
    @Column(length = DESCRIPTION_LENGTH)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected void formatEntryText(StringBuilder buf) {
        buf.append(person.getDisplayName());
        buf.append("@");
        if (!StringUtils.isEmpty(altOrgUnit))
            buf.append(altOrgUnit);
        else if (orgUnit != null)
            buf.append(orgUnit.getLabel());
        else if (org != null)
            buf.append(org.getDisplayName());
        else
            buf.append("*");
        buf.append(": ");
        buf.append(role);
    }

}
