package com.bee32.sem.asset.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 收款单
 * @author jack
 *
 */
@Entity
@DiscriminatorValue("CRED")
public class CreditNote extends FundFlow {

    private static final long serialVersionUID = 1L;

}
