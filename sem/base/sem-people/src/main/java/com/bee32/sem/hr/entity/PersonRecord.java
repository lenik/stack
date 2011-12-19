package com.bee32.sem.hr.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.sem.people.entity.Party;

/**
 * 人或组织的社会档案记录.
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "party_record_seq", allocationSize = 1)
public class PersonRecord
        extends MomentInterval {

    private static final long serialVersionUID = 1L;

    public static final int TEXT_LENGTH = 10000;

    PersonRecordCategory category = PersonRecordCategory.INFO;
    Party party;
    String text = "";

    @ManyToOne(optional = false)
    public PersonRecordCategory getCategory() {
        return category;
    }

    public void setCategory(PersonRecordCategory category) {
        if (category == null)
            throw new NullPointerException("category");
        this.category = category;
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
    @Column(length = TEXT_LENGTH, nullable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text == null)
            throw new NullPointerException("text");
        this.text = text;
    }

}