package com.bee32.sem.people.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.icsf.principal.Group;

@Entity
@DiscriminatorValue("ORG")
public class Org
        extends Party {

    private static final long serialVersionUID = 1L;

    OrgType type = predefined(OrgTypes.class).PARTNER;
    int size;

    List<OrgUnit> orgUnits = new ArrayList<OrgUnit>();
    Set<PersonRole> roles = new HashSet<PersonRole>();

    Group forWhichGroup;

    public Org() {
        this(null);
    }

    public Org(String label) {
        super(label);
        sidType = predefined(PartySidTypes.class).TAX_ID;
    }

    @Override
    public void retarget(Object o) {
        super.retarget(o);
        _retarget((Org) o);
    }

    private void _retarget(Org o) {
        _retargetMerge(orgUnits, o.orgUnits);
        _retargetMerge(roles, o.roles);
    }

    @ManyToOne
    public OrgType getType() {
        return type;
    }

    public void setType(OrgType type) {
        if (type == null)
            throw new NullPointerException("type");
        this.type = type;
    }

    /**
     * 企业规模：员工人数
     */
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * 所有部门（这里是平面结构，不按树排列）
     */
    @OneToMany(mappedBy = "org", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<OrgUnit> getOrgUnits() {
        return orgUnits;
    }

    public void setOrgUnits(List<OrgUnit> orgUnits) {
        if (orgUnits == null)
            throw new NullPointerException("orgUnits");
        this.orgUnits = orgUnits;
    }

    @OneToMany(mappedBy = "org", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public Set<PersonRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<PersonRole> roles) {
        if (roles == null)
            throw new NullPointerException("roles");
        this.roles = roles;
    }

    @OneToOne
    public Group getForWhichGroup() {
        return forWhichGroup;
    }

    public void setForWhichGroup(Group forWhichGroup) {
        this.forWhichGroup = forWhichGroup;
    }

}
