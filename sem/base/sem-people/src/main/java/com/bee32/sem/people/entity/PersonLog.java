package com.bee32.sem.people.entity;

import com.bee32.plover.orm.ext.color.BlueEntity;

public class PersonLog
        extends BlueEntity<Long> {

    private static final long serialVersionUID = 1L;

    Person person;
    PersonLogCategory category;
    String description;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public PersonLogCategory getCategory() {
        return category;
    }

    public void setCategory(PersonLogCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
