package com.bee32.zebra.io.sales;

import java.util.Date;

import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.oa.thread.Topic;
import com.bee32.zebra.tk.sea.FooControlled;
import com.tinylily.model.base.security.User;

public class SalesOrder
        extends FooControlled {

    private static final long serialVersionUID = 1L;

    int id;
    Topic topic;

    Organization org;
    Person person;

    Person seller;
    Person follower; // tracker, executive

    User op;

    // make-tasks
    // material-plans (locks)
    // deliveries 送货单/分次

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
