package com.bee32.sem.inventory.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.orm.ext.xp.XPool30;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "material_xp_seq", allocationSize = 1)
public class MaterialXP
        extends XPool30<Material> {

    private static final long serialVersionUID = 1L;

}
