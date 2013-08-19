package com.bee32.sem.account.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 应付核销类
 *
 * <p lang="en">
 *
 */
@Entity
@DiscriminatorValue("PAY")
public class VerificationPay
        extends Verification {

    private static final long serialVersionUID = 1L;

}
