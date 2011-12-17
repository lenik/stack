package com.bee32.sem.people.entity.personnel;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


/**
 * 劳动合同
 * @author jack
 *
 */
@Entity
@DiscriminatorValue("LAB")
public class LaborContract extends Contract {

    private static final long serialVersionUID = 1L;

}
