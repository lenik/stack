package com.bee32.sem.chance.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.xp.XPool30;

/**
 * 选型扩展属性池
 *
 * <p lang="en">
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "wanted_productxp_seq", allocationSize = 1)
public class WantedProductXP
        extends XPool30<WantedProduct> {

    private static final long serialVersionUID = 1L;

}
