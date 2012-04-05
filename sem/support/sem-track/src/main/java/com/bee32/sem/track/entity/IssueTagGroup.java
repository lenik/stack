package com.bee32.sem.track.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.bee32.plover.ox1.dict.ShortNameDict;

@Entity
public class IssueTagGroup
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    List<IssueTagname> tags = new ArrayList<IssueTagname>();

    @OneToMany(mappedBy = "group")
    public List<IssueTagname> getValues() {
        return tags;
    }

    public void setValues(List<IssueTagname> tags) {
        if (tags == null)
            throw new NullPointerException("tags");
        this.tags = tags;
    }

}
