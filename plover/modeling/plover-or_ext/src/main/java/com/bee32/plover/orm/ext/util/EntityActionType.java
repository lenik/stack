package com.bee32.plover.orm.ext.util;

public enum EntityActionType {

    /**
     * You should fill the DTO object with initial values.
     */
    CREATE,

    /**
     * You should marshal the primary Entity to a primary DTO object, at least.
     */
    LOAD,

    /**
     * You should unmarshal the primary DTO into the primary Entity, at least.
     */
    SAVE,

    /**
     * After the primary entity is saved.
     */
    POST_SAVE,

}
