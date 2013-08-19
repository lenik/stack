package com.bee32.sem.asset.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 收款单
 *
 * 企业收到款项时填制的单据。
 *
 * <p lang="en">
 * Credit Note
 */
@Entity
@DiscriminatorValue("CRED")
public class CreditNote
        extends FundFlow {

    private static final long serialVersionUID = 1L;

}
