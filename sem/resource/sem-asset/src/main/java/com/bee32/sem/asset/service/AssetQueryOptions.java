package com.bee32.sem.asset.service;

import java.io.Serializable;
import java.util.Date;

import com.bee32.plover.criteria.hibernate.GroupPropertyProjection;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.people.entity.Party;

public final class AssetQueryOptions
        implements Serializable {

    private static final long serialVersionUID = 1L;

    Date timestamp = new Date();

    AccountSubject subject;
    Party party;

    boolean subjectMerged;
    boolean partyMerged;

    public AssetQueryOptions(Date timestamp) {
        this(timestamp, null, null);
    }

    public AssetQueryOptions(Date timestamp, AccountSubject subject, Party party) {
        setTimestamp(timestamp);
        setSubject(subject);
        setParty(party);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        if (timestamp == null)
            throw new NullPointerException("timestamp");
        this.timestamp = timestamp;
    }

    public AccountSubject getSubject() {
        return subject;
    }

    public void setSubject(AccountSubject subject) {
        setSubject(subject, subject == null);
    }

    public void setSubject(AccountSubject subject, boolean merged) {
        this.subject = subject;
        this.subjectMerged = subject == null && merged;
    }

    public boolean isSubjectMerged() {
        return subjectMerged;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        setParty(party, party == null);
    }

    public void setParty(Party party, boolean merged) {
        this.party = party;
        this.partyMerged = party == null && merged;
    }

    public boolean isPartyMerged() {
        return partyMerged;
    }

    public GroupPropertyProjection getSubjectProjection() {
        if (subjectMerged)
            return null;
        else
            return new GroupPropertyProjection("subject");
    }

    public GroupPropertyProjection getPartyProjection() {
        if (partyMerged)
            return null;
        else
            return new GroupPropertyProjection("party");
    }

}
