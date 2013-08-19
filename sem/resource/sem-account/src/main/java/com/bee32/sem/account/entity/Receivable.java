package com.bee32.sem.account.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 应收单
 *
 * <p lang="en">
 */
@Entity
@DiscriminatorValue("RABLE")
public class Receivable
        extends AccountAble {

    private static final long serialVersionUID = 1L;

}
