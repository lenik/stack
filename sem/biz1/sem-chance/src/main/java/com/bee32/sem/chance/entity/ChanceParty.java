package com.bee32.sem.chance.entity;

import javax.free.Nullables;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.BlueEntity;
import com.bee32.sem.people.entity.Party;

@Entity
public class ChanceParty
        extends BlueEntity<Long> {

    private static final long serialVersionUID = 1L;

    private Chance chance;
    private Party party;
    private String category;

    public ChanceParty() {
    }

    @ManyToOne
    public Chance getSalesChance() {
        return chance;
    }

    public void setSalesChance(Chance chance) {
        this.chance = chance;
    }

    @ManyToOne
    public Party getCustomer() {
        return party;
    }

    public void setCustomer(Party party) {
        this.party = party;
    }

    @Column(length = 20)
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
