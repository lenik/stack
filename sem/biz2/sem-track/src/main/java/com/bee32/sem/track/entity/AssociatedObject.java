package com.bee32.sem.track.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.plover.orm.util.ITypeAbbrAware;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "issue-ao-seq", allocationSize = 1)
public class AssociatedObject
        implements ITypeAbbrAware {

    Issue issue;
    Class<?> clazz;
    String abbrClazz;
    Object key;

    public AssociatedObject() {
    }

    @OneToOne
    @JoinColumn(name = "issue", nullable = false)
    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    @Transient
    public Class<?> getAssociatedClass()
            throws ClassNotFoundException {
        if (clazz != null)
            return clazz;
        Class<?> expand = ABBR.expand(abbrClazz);
        return expand;
    }

    public void setAssociatedClass(Class<?> clazz) {
        this.clazz = clazz;
        abbrClazz = ABBR.abbr(clazz);
    }

    public String getAbbrClazz() {
        return abbrClazz;
    }

    public void setAbbrClazz(String abbrClazz) {
        this.abbrClazz = abbrClazz;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

}
