package com.seccaproject.plover.arch.ui;

import java.net.URL;

public abstract class AbstractIconMap
        implements IIconMap {

    @Override
    public URL getIcon() {
        return getIcon(null, 0, 0);
    }

    @Override
    public URL getIcon(String variant) {
        return getIcon(variant, 0, 0);
    }

}
