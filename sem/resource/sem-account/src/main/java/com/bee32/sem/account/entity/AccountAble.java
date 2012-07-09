package com.bee32.sem.account.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 应收单应付单基类
 * @author jack
 *
 */
@Entity
@DiscriminatorValue("ABLE")
public class AccountAble extends CurrentAccount {

    private static final long serialVersionUID = 1L;

}
