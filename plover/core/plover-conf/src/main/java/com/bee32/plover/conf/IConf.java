package com.bee32.plover.conf;

import java.io.Serializable;
import java.util.Collection;

import com.bee32.plover.model.IModel;

public interface IConf
        extends IModel, Serializable {

    Collection<String> getKeys();

    Object get(String key);

}
