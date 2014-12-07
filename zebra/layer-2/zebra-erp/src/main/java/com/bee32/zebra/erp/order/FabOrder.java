package com.bee32.zebra.erp.order;

import java.util.Date;

import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.oa.thread.Topic;
import com.bee32.zebra.tk.foo.FooControlled;
import com.tinylily.model.base.security.User;

public class FabOrder
        extends FooControlled {

    private static final long serialVersionUID = 1L;

    Topic topic;
    Organization org;
    Person person;

    Person seller;
    Person follower; // tracker, executive

    User op;

    // make-tasks
    // material-plans (locks)
    // deliveries 送货单/分次

    public Date getOrderTime() {
        return super.getBeginTime();
    }

    public Date getDeadline() {
        return super.getEndTime();
    }

    public User getOp() {
        return op;
    }

}
