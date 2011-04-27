package com.bee32.plover.orm.ext.util;

public enum EntityAction {

    /**
     * You should fill the DTO object with initial values.
     */
    Create,

    /**
     * You should marshal the primary Entity to a primary DTO object, at least.
     */
    Load,

    /**
     * You should unmarshal the primary DTO into the primary Entity, at least.
     */
    Save,

}
