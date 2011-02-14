package com.bee32.plover.javascript;

import java.util.Collection;

public interface IJavascriptUsage
        extends IDependent<IJavascriptLibrary> {

    @Override
    Collection<IJavascriptLibrary> getDependencies();

}
