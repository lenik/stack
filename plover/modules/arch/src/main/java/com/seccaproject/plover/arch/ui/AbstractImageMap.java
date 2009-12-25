package com.seccaproject.plover.arch.ui;

import java.net.URL;

public abstract class AbstractImageMap
        implements IImageMap {

    @Override
    public URL getImage() {
        return getImage(null, 0, 0);
    }

    @Override
    public URL getImage(String variant) {
        return getImage(variant, 0, 0);
    }

}
