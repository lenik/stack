package com.bee32.plover.bookstore;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.arch.util.Identity;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.color.UIEntityAuto;

@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "book_seq", allocationSize = 1)
public class Book
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    private String name;
    private String content;

    public Book() {
    }

    public Book(String name, String content) {
        this.name = name;
        this.content = content;
    }

    @Basic(optional = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    protected Serializable naturalId() {
        if (name == null)
            return new Identity(this);
        return name;
    }

    @Override
    protected CriteriaElement selector(String prefix) {
        if (name == null)
            throw new NullPointerException("name");
        return new Equals(prefix + "name", name);
    }

}
