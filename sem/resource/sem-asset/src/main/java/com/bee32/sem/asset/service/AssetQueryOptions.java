package com.bee32.sem.asset.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bee32.plover.criteria.hibernate.GroupPropertyProjection;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.people.entity.Party;

public final class AssetQueryOptions
        implements Serializable {

    private static final long serialVersionUID = 1L;

    Date timestamp = new Date();

    List<AccountSubject> subjects;
    List<Party> parties;

    boolean subjectMerged;
    boolean partyMerged;

    public AssetQueryOptions(Date timestamp) {
        this(timestamp, null, null);
    }

    public AssetQueryOptions(Date timestamp, List<AccountSubject> subjects, List<Party> parties) {
        setTimestamp(timestamp);
        setSubjects(subjects);
        setParties(parties);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        if (timestamp == null)
            throw new NullPointerException("timestamp");
        this.timestamp = timestamp;
    }

    public List<AccountSubject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<AccountSubject> subjects) {
        setSubjects(subjects, subjects == null);
    }

    public void setSubjects(List<AccountSubject> subjects, boolean merged) {
        this.subjects = subjects;
        this.subjectMerged = subjects == null && merged;
    }

    public boolean isSubjectMerged() {
        return subjectMerged;
    }

    public boolean addSubject(AccountSubject subject) {
        if (subjects == null) {
            subjects = new ArrayList<AccountSubject>();
            subjectMerged = false;
        }
        if (subjects.contains(subject))
            return false;
        subjects.add(subject);
        return true;
    }

    public boolean removeSubject(AccountSubject subject) {
        if (subjects == null)
            return false;
        else
            return subjects.remove(subject);
    }

    public List<Party> getParties() {
        return parties;
    }

    public void setParties(List<Party> parties) {
        setParties(parties, parties == null);
    }

    public void setParties(List<Party> parties, boolean merged) {
        this.parties = parties;
        this.partyMerged = parties == null && merged;
    }

    public boolean isPartyMerged() {
        return partyMerged;
    }

    public boolean addParty(Party party) {
        if (parties == null) {
            parties = new ArrayList<Party>();
            partyMerged = false;
        }
        if (parties.contains(party))
            return false;
        parties.add(party);
        return true;
    }

    public boolean removeParty(Party party) {
        if (parties == null)
            return false;
        else
            return parties.remove(party);
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
