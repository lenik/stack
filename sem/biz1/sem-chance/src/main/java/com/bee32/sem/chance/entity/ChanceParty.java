package com.bee32.sem.chance.entity;

import javax.free.Nullables;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.BlueEntity;
import com.bee32.sem.people.entity.Party;

@Entity
public class ChanceParty
        extends BlueEntity<Long> {

    private static final long serialVersionUID = 1L;

    Chance chance;
    Party party;
    String category;

    @NaturalId
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public Chance getSalesChance() {
        return chance;
    }

    public void setSalesChance(Chance chance) {
        if( chance == null)
            throw new NullPointerException("can't set null to ChanceParty.chance");
        this.chance = chance;
    }

    @NaturalId
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public Party getCustomer() {
        return party;
    }

    public void setCustomer(Party party) {
        if(party == null)
            throw new NullPointerException("can't set null to ChanceParty.party");
        this.party = party;
    }

    @Column(length = 20, nullable = false)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    protected Boolean naturalEquals(EntityBase<Long> other) {
        ChanceParty otherDetail = (ChanceParty) other;

        Long chanceId = chance.getId();
        Long otherChanceId = otherDetail.getSalesChance().getId();
        if (!Nullables.equals(chanceId, otherChanceId))
            return false;

        if (!Nullables.equals(party.getId(), otherDetail.getCustomer().getId()))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = 0;

        if (chance != null) {
            Long chanceId = chance.getId();
            if (chanceId != null)
                hash = hash * 37 + chanceId.hashCode();
        }

        Number customerId = party.getId();
        if (customerId != null)
            hash = hash * 37 + customerId.hashCode();

        return hash;
    }

}
