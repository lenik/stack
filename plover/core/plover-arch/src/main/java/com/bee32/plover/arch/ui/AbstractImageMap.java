package com.bee32.plover.arch.ui;

import java.net.URL;
import java.util.TreeMap;

import com.bee32.plover.arch.ui.res.ImageVariant;

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

    public abstract TreeMap<ImageVariant, URL> getVariantMap();

    @Override
    public URL getImage(String variant, int widthHint, int heightHint) {
        TreeMap<ImageVariant, URL> variantMap = getVariantMap();

        ImageVariant wantKey = new ImageVariant(variant, widthHint, heightHint);

        while (wantKey != null) {
            ImageVariant searchKey = variantMap.floorKey(wantKey);

            if (searchKey.implies(wantKey))
                return variantMap.get(searchKey);

            wantKey = wantKey.getParent();
        }
        return null;
    }

}
