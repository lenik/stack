package com.bee32.zebra.oa.thread;

import net.bodz.lily.model.base.CoRelation;
import net.bodz.lily.model.contact.Organization;
import net.bodz.lily.model.contact.Person;

/**
 * 项目参与方
 */
public class TopicParty
        extends CoRelation<Long> {

    private static final long serialVersionUID = 1L;

    private Topic topic;
    private Person person;
    private Organization org;
    private String description;

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
