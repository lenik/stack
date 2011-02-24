package com.bee32.plover.javascript;

import java.net.URL;

public interface IJavascriptDependency
        extends IJavascriptDependant, IRelativeURL {

    @Override
    URL getURL(URL context);

}
