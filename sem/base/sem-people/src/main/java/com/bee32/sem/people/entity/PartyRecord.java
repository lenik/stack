package com.bee32.sem.people.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.plover.orm.ext.color.MomentInterval;

/**
 * 人或组织的社会档案记录.
 */
@Entity
public class PartyRecord
        extends MomentInterval {

    private static final long serialVersionUID = 1L;

    PartyRecordCategory category = PartyRecordCategory.INFO;
    Date date;
    Party party;
    String text = "";

    @ManyToOne
    public PartyRecordCategory getCategory() {
        return category;
    }

    public void setCategory(PartyRecordCategory category) {
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
    @Column(length = 10000, nullable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text == null)
            throw new NullPointerException("text");
        this.text = text;
    }

}
