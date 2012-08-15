package com.bee32.sem.account.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 收款单付款单,应收应付票据基类
 *
 * @author jack
 *
 */
@Entity
@DiscriminatorValue("ED")
public class AccountEd
        extends CurrentAccount {

    private static final long serialVersionUID = 1L;

}
