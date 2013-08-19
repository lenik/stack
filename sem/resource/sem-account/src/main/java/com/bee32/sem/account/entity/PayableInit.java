package com.bee32.sem.account.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 应付初始化
 *
 * <p lang="en">
 */
@Entity
@DiscriminatorValue("PINIT")
public class PayableInit
        extends Payable {

    private static final long serialVersionUID = 1L;

}
