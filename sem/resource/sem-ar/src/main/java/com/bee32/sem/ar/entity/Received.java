package com.bee32.sem.ar.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 收款单
 * @author jack
 *
 */
@Entity
@DiscriminatorValue("ED")
public class Received extends ARBase {

    private static final long serialVersionUID = 1L;


}
