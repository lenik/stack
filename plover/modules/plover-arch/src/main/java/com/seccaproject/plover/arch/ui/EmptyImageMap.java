package com.seccaproject.plover.arch.ui;

import java.net.URL;

public class EmptyImageMap
        extends AbstractImageMap {

    @Override
    public URL getImage(String variant, int widthHint, int heightHint) {
        return null;
    }

    static final EmptyImageMap instance = new EmptyImageMap();

    public static EmptyImageMap getInstance() {
        return instance;
    }

}
