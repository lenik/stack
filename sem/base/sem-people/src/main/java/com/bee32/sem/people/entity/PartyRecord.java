package com.bee32.sem.people.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.And;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.ox1.color.MomentInterval;

/**
 * 人事历史档案
 *
 * 在公司内部记录的自然人或组织机构发生的重要事件。
 *
 * <p lang="en">
 * Party Record
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "party_record_seq", allocationSize = 1)
public class PartyRecord
        extends MomentInterval {

    private static final long serialVersionUID = 1L;

    public static final int TEXT_LENGTH = 10000;

    PartyRecordCategory category = predefined(PartyRecordCategories.class).INFO;
    Party party;
    String text = "";

    @Override
    public void populate(Object source) {
        if (source instanceof PartyRecord)
            _populate((PartyRecord) source);
        else
            super.populate(source);
    }

    protected void _populate(PartyRecord o) {
        super._populate(o);
        category = o.category;
        party = o.party;
        text = o.text;
    }

    /**
     * 分类
     *
     * 社会档案记录的分类。
     */
    @ManyToOne(optional = false)
    public PartyRecordCategory getCategory() {
        return category;
    }

    public void setCategory(PartyRecordCategory category) {
        if (category == null)
            throw new NullPointerException("category");
        this.category = category;
    }

    /**
     * 人或组织
     *
     * 社会档案记录的相关人或组织。
     */
    @ManyToOne(optional = false)
    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        if (party == null)
            throw new NullPointerException("party");
        this.party = party;
    }

    /**
     * 正文
     *
     * 社会档案记录的主要内容。
     */
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

    @Override
    protected CEntity<?> owningEntity() {
        return getParty();
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(party), //
                getBeginTime());
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        return And.of(//
                new Equals(prefix + "party", party), //
                new Equals(prefix + "beginTime", getBeginTime()));
    }

}
