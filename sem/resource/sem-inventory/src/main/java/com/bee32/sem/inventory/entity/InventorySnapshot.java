package com.bee32.sem.inventory.entity;

import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.ext.color.Green;

@MappedSuperclass
@Green
public class InventorySnapshot
        extends EntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    InventorySnapshotType type;

}
