package com.bee32.zebra.erp.order;

import java.math.BigDecimal;
import java.util.Date;

import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.tk.foo.FooControlled;

public class Deliver
        extends FooControlled {

    private static final long serialVersionUID = 1L;

    FabOrder fabOrder;

    Organization company;
    Person person;

    Date arrivalDate;
    // Take-Out stock job
    // Account-Ticket

    BigDecimal total;

}
