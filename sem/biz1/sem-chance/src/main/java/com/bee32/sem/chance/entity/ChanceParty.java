package com.bee32.sem.chance.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.c.CEntityAuto;
import com.bee32.plover.ox1.color.Yellow;
import com.bee32.sem.people.entity.Party;

/**
 * 机会客户关联
 */
@Entity
@Yellow
@SequenceGenerator(name = "idgen", sequenceName = "chance_party_seq", allocationSize = 1)
public class ChanceParty
        extends CEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int ROLE_LENGTH = 20;

    Chance chance;
    Party party;
    String role;

X-Population

    @NaturalId
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public Chance getChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        if (chance == null)
            throw new NullPointerException("can't set null to ChanceParty.chance");
        this.chance = chance;
    }

    @NaturalId(mutable = true)
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        if (party == null)
            throw new NullPointerException("can't set null to ChanceParty.party");
        this.party = party;
    }

    @Column(length = ROLE_LENGTH, nullable = false)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role == null)
            throw new NullPointerException("can't set Null to ChanceParty.role");
        this.role = role;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(naturalId(chance), naturalId(party));
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        return selectors(//
                selector(prefix + "chance", chance), //
                selector(prefix + "party", party));
    }

}
