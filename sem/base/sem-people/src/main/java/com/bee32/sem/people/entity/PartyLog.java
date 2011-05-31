package com.bee32.sem.people.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.plover.orm.ext.color.BlueEntity;

@Entity
public class PartyLog
        extends BlueEntity<Long> {

    private static final long serialVersionUID = 1L;

    PartyLogCategory category;
    Date date;
    Party party;
    String description;

    @ManyToOne
    public PartyLogCategory getCategory() {
        return category;
    }

    public void setCategory(PartyLogCategory category) {
        if (category == null)
            throw new NullPointerException("category");
        this.category = category;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Basic(optional = false)
    @Column(nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        if (date == null)
            throw new NullPointerException("date");
        this.date = date;
    }

    @ManyToOne(optional = false)
    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        if (party == null)
            throw new NullPointerException("party");
        this.party = party;
    }

    @Basic(optional = false)
    @Column(length = 200, nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null)
            throw new NullPointerException("description");
        this.description = description;
    }

}
