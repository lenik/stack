package com.bee32.sem.account.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 收款单
 *
 * <p lang="en">
 */
@Entity
@DiscriminatorValue("RED")
public class Received
        extends AccountEd {

    private static final long serialVersionUID = 1L;

}
