package com.bee32.plover.conf;

import java.io.Serializable;
import java.util.Collection;

import com.bee32.plover.arch.IComponent;

public interface IConf
        extends IComponent, Serializable {

    Collection<String> getKeys();

    Object get(String key);

}
