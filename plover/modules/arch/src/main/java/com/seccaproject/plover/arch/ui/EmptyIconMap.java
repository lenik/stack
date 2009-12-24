package com.seccaproject.plover.arch.ui;

import java.net.URL;

public class EmptyIconMap
        extends AbstractIconMap {

    @Override
    public URL getIcon(String variant, int widthHint, int heightHint) {
        return null;
    }

    static final EmptyIconMap instance = new EmptyIconMap();

    public static EmptyIconMap getInstance() {
        return instance;
    }

}
