package com.bee32.plover.orm.entity;

import java.io.Serializable;

public class EntitySynth<E extends Entity<K>, K extends Serializable> {

    /**
     * @see DataHandler#fillFormExtra.
     */
    public void checkOut(E entity) {
    }

    public void checkIn(E entity) {

    }

    public void postSave(E entity) {

    }

}
