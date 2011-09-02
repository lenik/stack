package com.bee32.plover.orm.feaCat;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "cat_seq", allocationSize = 1)
public class Cat
        extends SuperEntity<Long> {

    private static final long serialVersionUID = 1L;

    String color;

    Cat leader;
    Cat parent;

    Set<Cat> children;

    CaveAddr addr;

    public Cat() {
    }

    public Cat(String name, String color) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
        this.color = color;
    }

    @NaturalId
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @ManyToOne
    public Cat getParent() {
        return parent;
    }

    public void setParent(Cat parent) {
        this.parent = cast(parent);
    }

    @ManyToOne
    public Cat getLeader() {
        return leader;
    }

    public void setLeader(Cat leader) {
        this.leader = leader;
    }

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    public Set<Cat> getChildren() {
        if (children == null) {
            synchronized (this) {
                if (children == null) {
                    // Using LinkedHashSet to keep order for test.
                    children = new LinkedHashSet<Cat>();
                }
            }
        }
        return children;
    }

    public void setChildren(Set<Cat> children) {
        this.children = children;
    }

    @Override
    public Serializable naturalId() {
        if (name == null)
            return new DummyId(this);
        return name;
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (name == null)
            throw new NullPointerException("name");
        return new Equals(prefix + "name", name);
    }

    @Transient
    protected String getInternal() {
        return "Int";
    }

    @Embedded
    public CaveAddr getAddr() {
        return addr;
    }

    public void setAddr(CaveAddr addr) {
        this.addr = addr;
    }

}
