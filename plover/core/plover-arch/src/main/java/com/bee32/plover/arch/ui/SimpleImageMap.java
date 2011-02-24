package com.bee32.plover.arch.ui;

import java.net.URL;
import java.util.TreeMap;

import com.bee32.plover.arch.ui.res.ImageVariant;

public class SimpleImageMap
        extends AbstractImageMap {

    private static final TreeMap<ImageVariant, URL> emptyMap = new TreeMap<ImageVariant, URL>();

    private static TreeMap<ImageVariant, URL> variantMap = emptyMap;

    @Override
    public TreeMap<ImageVariant, URL> getVariantMap() {
        return variantMap;
    }

    public void addImage(ImageVariant variant, URL url) {
        if (variantMap == emptyMap)
            variantMap = new TreeMap<ImageVariant, URL>();
        variantMap.put(variant, url);
    }

    static final SimpleImageMap instance = new SimpleImageMap();

    public static SimpleImageMap getInstance() {
        return instance;
    }

}
