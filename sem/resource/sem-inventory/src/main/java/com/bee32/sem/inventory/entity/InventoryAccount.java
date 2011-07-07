package com.bee32.sem.inventory.entity;

import javax.persistence.MappedSuperclass;

import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.thing.entity.Thing;

@MappedSuperclass
public class InventoryAccount<T extends Thing<?>>
        extends TxEntity {

    private static final long serialVersionUID = 1L;

}
