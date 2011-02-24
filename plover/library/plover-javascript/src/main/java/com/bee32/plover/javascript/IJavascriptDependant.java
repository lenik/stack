package com.bee32.plover.javascript;

import java.util.Collection;

public interface IJavascriptDependant
        extends IDependent<IJavascriptDependency> {

    @Override
    Collection<IJavascriptDependency> getDependencies();

}
