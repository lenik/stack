package com.bee32.plover.orm.config;

import java.util.Collection;

import com.bee32.plover.arch.util.IPriority;

public abstract interface IEntityProcessor
        extends IPriority {

    Collection<?> getEventListeners();

}
