package com.bee32.icsf.access.alt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.ox1.color.Green;

@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "r_acl_seq", allocationSize = 1)
public class R_ACL
        extends EntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    Principal principal;
    List<R_ACE> entries = new ArrayList<R_ACE>();

    @NaturalId
    @ManyToOne(optional = false)
    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        if (principal == null)
            throw new NullPointerException("principal");
        this.principal = principal;
    }

    @OneToMany(orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<R_ACE> getEntries() {
        return entries;
    }

    public void setEntries(List<R_ACE> entries) {
        if (entries == null)
            throw new NullPointerException("entries");
        this.entries = entries;
    }

    @Override
    protected Serializable naturalId() {
        return naturalId(principal);
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (principal == null)
            throw new NullPointerException("principal");
        return selector(prefix + "principal", principal);
    }

}
