package com.bee32.zebra.oa.thread;

import java.io.Serializable;

import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;

/**
 * 项目参与方
 */
public class TopicParty
        // extends CoObject
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int N_DESCRIPTION = 60;

    private Long id;
    private Topic topic;
    private Person person;
    private Organization org;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
