package com.bee32.sem.account.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 付款单
 * @author jack
 *
 */
@Entity
@DiscriminatorValue("P-ED")
public class Paied extends AccountRecePay {

    private static final long serialVersionUID = 1L;

}
