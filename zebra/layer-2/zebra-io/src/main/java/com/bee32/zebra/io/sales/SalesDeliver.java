package com.bee32.zebra.io.sales;

import java.math.BigDecimal;
import java.util.Date;

import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.tk.sea.FooControlled;

public class SalesDeliver
        extends FooControlled {

    private static final long serialVersionUID = 1L;

    int id;
    SalesOrder fabOrder;

    Organization company;
    Person person;

    Date arrivalDate;
    // Take-Out stock job
    // Account-Ticket

    BigDecimal total;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
