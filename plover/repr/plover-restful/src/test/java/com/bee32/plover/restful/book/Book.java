package com.bee32.plover.restful.book;

import com.bee32.plover.orm.entity.IEntity;

public class Book
        implements IEntity<String> {

    private static final long serialVersionUID = 1L;

    private String name;
    private String content;

    public Book(String name, String content) {
        this.name = name;
        this.content = content;
    }

    @Override
    public String getPrimaryKey() {
        return name;
    }

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
    public String toString() {
        return name+" :: " + content;
    }

}
