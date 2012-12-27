package com.bee32.sem.material.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.xp.XPool30;

/**
 * 物料属性池
 *
 * 用于提供附加的物料属性。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "materialxp_seq", allocationSize = 1)
public class MaterialXP
        extends XPool30<Material> {

    private static final long serialVersionUID = 1L;

}
