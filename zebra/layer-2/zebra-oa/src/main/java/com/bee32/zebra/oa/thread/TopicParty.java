package com.bee32.zebra.oa.thread;

import java.io.Serializable;

import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;

public class TopicParty
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int N_DESCRIPTION = 60;

    Topic topic;
    Person person;
    Organization org;
    String description;

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
