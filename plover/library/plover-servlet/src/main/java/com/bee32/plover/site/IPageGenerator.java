package com.bee32.plover.site;

import java.util.Map;

import com.bee32.plover.arch.util.IOrdered;

public interface IPageGenerator
        extends IOrdered {

    String generate(Map<String, ?> args);

}
