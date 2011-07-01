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
public class Place
        extends TreeEntityAuto<Integer, Place> {

    private static final long serialVersionUID = 1L;

}
