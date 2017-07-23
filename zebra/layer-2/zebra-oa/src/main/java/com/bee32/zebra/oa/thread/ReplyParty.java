package com.bee32.zebra.oa.thread;

import net.bodz.lily.model.base.CoRelation;
import net.bodz.lily.model.contact.Organization;
import net.bodz.lily.model.contact.Person;

/**
 * 项目参与方
 */
public class ReplyParty
        extends CoRelation<Long> {

    private static final long serialVersionUID = 1L;

    private Reply reply;
    private Person person;
    private Organization org;

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
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

}
