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

    boolean verifiedOnly = false;   //true:查询审核的项目   false:查询所有项目
    List<AccountSubject> subjects;
    boolean recursive;
    List<Party> parties;

    boolean subjectVisible = true;
    boolean partyVisible;

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

    public boolean isVerifiedOnly() {
        return verifiedOnly;
    }

    public void setVerifiedOnly(boolean verifiedOnly) {
        this.verifiedOnly = verifiedOnly;
    }

    public List<AccountSubject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<AccountSubject> subjects) {
        this.subjects = subjects;
    }

    public boolean isRecursive() {
        return recursive;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    public boolean isSubjectVisible() {
        return subjectVisible;
    }

    public boolean addSubject(AccountSubject subject) {
        if (subjects == null) {
            subjects = new ArrayList<AccountSubject>();
            subjectVisible = true;
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
        setParties(parties, parties != null);
    }

    public void setParties(List<Party> parties, boolean visible) {
        this.parties = parties;
        this.partyVisible = parties != null || visible;
    }

    public boolean isPartyVisible() {
        return partyVisible;
    }

    public boolean addParty(Party party) {
        if (parties == null) {
            parties = new ArrayList<Party>();
            partyVisible = true;
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
        if (subjectVisible)
            return new GroupPropertyProjection("subject");
        else
            return null;
    }

    public GroupPropertyProjection getPartyProjection() {
        if (partyVisible)
            return new GroupPropertyProjection("party");
        else
            return null;
    }

}
