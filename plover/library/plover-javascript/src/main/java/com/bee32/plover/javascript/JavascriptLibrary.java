package com.bee32.plover.javascript;

import java.net.URL;
import java.util.Collection;

public class JavascriptLibrary
        extends JavascriptDependant
        implements IJavascriptDependency {

    @Override
    public Collection<IJavascriptDependency> getDependencies() {
        return null;
    }

    @Override
    public URL getURL(URL context) {
        return null;
    }

}
