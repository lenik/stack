package com.bee32.sem.account.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 应收单
 */
@Entity
@DiscriminatorValue("R---")
public class Receivable
        extends AccountRecePay {

    private static final long serialVersionUID = 1L;

}
