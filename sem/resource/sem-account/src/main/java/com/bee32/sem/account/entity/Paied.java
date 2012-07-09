package com.bee32.sem.account.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 付款单
 * @author jack
 *
 */
@Entity
@DiscriminatorValue("PED")
public class Paied extends AccountEd {

    private static final long serialVersionUID = 1L;

}
