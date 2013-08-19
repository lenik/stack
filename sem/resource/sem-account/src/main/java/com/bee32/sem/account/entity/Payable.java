package com.bee32.sem.account.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 应付单
 *
 * <p lang="en">
 */
@Entity
@DiscriminatorValue("PABLE")
public class Payable
        extends AccountAble {

    private static final long serialVersionUID = 1L;

}
