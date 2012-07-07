package com.bee32.sem.account.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 应付单
 * @author jack
 *
 */
@Entity
@DiscriminatorValue("P---")
public class Payable extends AccountRecePay {

    private static final long serialVersionUID = 1L;

}
