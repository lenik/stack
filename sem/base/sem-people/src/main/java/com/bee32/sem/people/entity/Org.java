package com.bee32.sem.people.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.ox1.principal.Group;

@Entity
@DiscriminatorValue("ORG")
public class Org
        extends Party {

    private static final long serialVersionUID = 1L;

    OrgType type = OrgType.PARTNER;
    int size;

    // List<OrgUnit> orgUnits = new ArrayList<OrgUnit>();

    Set<PersonRole> roles = new HashSet<PersonRole>();

    Group forWhichGroup;

    public Org() {
        super();
    }

    public Org(String name) {
        super(name);
    }

    {
        sidType = PartySidType.TAX_ID;
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
