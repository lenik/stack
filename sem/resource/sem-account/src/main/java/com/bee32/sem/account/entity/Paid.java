package com.bee32.sem.account.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 付款单
 *
 * <p lang="en">
 */
@Entity
@DiscriminatorValue("PED")
public class Paid
        extends AccountEd {

    private static final long serialVersionUID = 1L;

}
