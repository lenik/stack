package com.bee32.sem.inventory.entity;

import javax.persistence.Entity;

import com.bee32.sem.store.entity.Store;

@Entity
public class Inventory
        extends Store<InventoryXP> {

    private static final long serialVersionUID = 1L;

}
