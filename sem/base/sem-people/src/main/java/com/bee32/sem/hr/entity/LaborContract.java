package com.bee32.sem.hr.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bee32.sem.misc.Contract;

/**
 * 劳动合同
 *
 * @author jack
 */
@Entity
@DiscriminatorValue("LAB")
public class LaborContract
        extends Contract {

    private static final long serialVersionUID = 1L;

}