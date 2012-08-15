package com.bee32.sem.account.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 应收核销类
 *
 * @author jack
 *
 */
@Entity
@DiscriminatorValue("RECE")
public class VerificationRece
        extends Verification {

    private static final long serialVersionUID = 1L;

}
