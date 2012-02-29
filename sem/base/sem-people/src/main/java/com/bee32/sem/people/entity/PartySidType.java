package com.bee32.sem.people.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.ShortNameDict;

@Entity
public class PartySidType
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public static final char PERSON = 'P';
    public static final char ORG = 'O';

    char category;

    public PartySidType() {
        super();
    }

    public PartySidType(char category, String name, String label, String description) {
        super(name, label, description);
        this.category = category;
    }

    @Column(nullable = false)
    public char getCategory() {
        return category;
    }

    public void setCategory(char category) {
        this.category = category;
    }

}
