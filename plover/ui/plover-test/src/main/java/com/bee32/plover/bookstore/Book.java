package com.bee32.plover.bookstore;

import javax.free.Nullables;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.Green;
import com.bee32.plover.orm.ext.color.UIEntityAuto;

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

    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    protected Boolean naturalEquals(EntityBase<Integer> otherEntity) {
        Book o = (Book) otherEntity;

        if (!Nullables.equals(name, o.name))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

}
