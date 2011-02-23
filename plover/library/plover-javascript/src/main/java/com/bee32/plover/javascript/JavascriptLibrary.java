package com.bee32.plover.javascript;

import java.net.URL;
import java.util.Collection;

public class JavascriptLibrary
        extends AbstractJavascriptUsage
        implements IJavascriptLibrary {

    @Override
    public Collection<IJavascriptLibrary> getDependencies() {
        return null;
    }

    @Override
    public URL getLibraryURL() {
        return null;
    }

}
