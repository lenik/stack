package com.bee32.zebra.erp.order;

import java.math.BigDecimal;
import java.util.Date;

import com.bee32.zebra.erp.stock.Artifact;
import com.bee32.zebra.oa.model.contact.Organization;
import com.bee32.zebra.oa.model.contact.Person;

public class Deliver {

    FabOrder fabOrder;

    Organization company;
    Person person;

    Date arrivalDate;
    // Take-Out stock job
    // Account-Ticket

    BigDecimal total;

}

class DeliverItem { // extends AbstractItem

    Artifact material;
    FabOrder fabItem;

}