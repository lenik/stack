package com.bee32.sem.inventory.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.tree.TreeEntityAuto;

/**
 * Usage:
 * <ul>
 * <li>Unrelated regions could be disconnected.
 * </ul>
 */
@Entity
public class StockLocation
        extends TreeEntityAuto<Integer, StockLocation> {

    private static final long serialVersionUID = 1L;

}
