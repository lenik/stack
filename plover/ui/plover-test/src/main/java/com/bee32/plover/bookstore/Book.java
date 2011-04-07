package com.bee32.plover.bookstore;

import javax.free.Nullables;
import javax.persistence.Basic;
import javax.persistence.Entity;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.EntityFormat;
import com.bee32.plover.util.PrettyPrintStream;

@Entity
public class Book
        extends EntityBean<Integer> {

    private static final long serialVersionUID = 1L;

    private String name;
    private String content;

    public Book() {
    }

    public Book(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public Book(int id, String name, String content) {
        this.id = id;
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
    protected int hashCodeEntity() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    protected boolean equalsEntity(EntityBean<Integer> otherEntity) {
        Book o = (Book) otherEntity;

        if (!Nullables.equals(name, o.name))
            return false;

        if (!Nullables.equals(content, o.content))
            return false;

        return true;
    }

    @Override
    public void toString(PrettyPrintStream out, EntityFormat format) {
        String s = id + ") " + name + " :: " + content;
        out.print(s);
    }

}
