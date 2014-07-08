package com.bee32.zebra.erp.order;

import java.util.Date;

import com.bee32.zebra.oa.model.contact.Organization;
import com.bee32.zebra.oa.model.contact.Person;
import com.bee32.zebra.oa.model.thread.Topic;
import com.tinylily.model.base.CoMomentInterval;

public class FabOrder
        extends CoMomentInterval {

    private static final long serialVersionUID = 1L;

    public final int S_DRAFT = S_INIT;
    public final int S_START = S_INIT + 1;
    public final int S_DONE = S_INIT + 2;
    public final int S_CANCEL = S_INVALID;

    Topic topic;
    Organization org;
    Person person;

    Person seller;
    Person follower; // tracker, executive

    // make-tasks
    // material-plans (locks)
    // deliveries 送货单/分次

    public Date getOrderTime() {
        return super.getBeginTime();
    }

    public Date getDeadline() {
        return super.getEndTime();
    }

}
