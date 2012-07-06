package com.bee32.sem.account.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 收款单
 */
@Entity
@DiscriminatorValue("R-ED")
public class Received
        extends AccountReceive {

    private static final long serialVersionUID = 1L;

}
