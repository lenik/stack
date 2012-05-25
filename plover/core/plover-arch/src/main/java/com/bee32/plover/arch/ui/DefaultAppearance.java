package com.bee32.plover.arch.ui;

import com.bee32.plover.arch.util.res.IProperties;

public class DefaultAppearance
        extends AbstractAppearance {

    String displayName;
    String description;
    IImageMap imageMap;
    IRefdocs refdocs;

    public DefaultAppearance() {
        super(null);
    }

    public DefaultAppearance(IAppearance parent) {
        super(parent);
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public IImageMap getImageMap() {
        return imageMap;
    }

    public void setImageMap(IImageMap imageMap) {
        this.imageMap = imageMap;
    }

    @Override
    public IRefdocs getRefdocs() {
        return refdocs;
    }

    public void setRefdocs(IRefdocs refdocs) {
        this.refdocs = refdocs;
    }

    public void populate(IProperties properties, String prefix) {
        displayName = properties.get(prefix + "displayName");
        description = properties.get(prefix + "description");

        SimpleImageMap simpleImageMap = new SimpleImageMap();
        imageMap = simpleImageMap;
        // TreeMap<ImageVariant, URL> variantMap = simpleImageMap.getVariantMap();
        // new ImageVariant(variantName, widthHint, heightHint);

        SimpleRefdocs simpleRefdocs = new SimpleRefdocs();
        refdocs = simpleRefdocs;
    }

}
