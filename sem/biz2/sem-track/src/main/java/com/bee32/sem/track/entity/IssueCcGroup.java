package com.bee32.sem.track.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.icsf.principal.Group;
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 问题抄送组
 *
 * 参与问题的组，将问题抄送给这些组的成员。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "issue_cc_group_seq", allocationSize = 1)
public class IssueCcGroup
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Issue issue;
    Group group;

    @NaturalId
    @ManyToOne(optional = false)
    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        if (issue == null)
            throw new NullPointerException("issue");
        this.issue = issue;
    }

    @NaturalId
    @ManyToOne(optional = false)
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        if (group == null)
            throw new NullPointerException("group");
        this.group = group;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(issue), //
                naturalId(group));
    }

}
