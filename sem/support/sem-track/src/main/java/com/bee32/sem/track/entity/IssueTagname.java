package com.bee32.sem.track.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.plover.ox1.color.UIEntityAuto;

@Entity
public class IssueTagname
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    IssueTagGroup group;

    @ManyToOne(optional = false)
    public IssueTagGroup getGroup() {
        return group;
    }

    public void setCategory(IssueTagGroup group) {
        if (group == null)
            throw new NullPointerException("group");
        this.group = group;
    }

}
