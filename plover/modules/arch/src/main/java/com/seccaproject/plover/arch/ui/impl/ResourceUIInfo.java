package com.seccaproject.plover.arch.ui.impl;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.seccaproject.plover.arch.ui.IIconMap;
import com.seccaproject.plover.arch.ui.IRefdocs;
import com.seccaproject.plover.arch.ui.IUIInfo;
import com.seccaproject.plover.arch.ui.PlainRefdocs;

/**
 * 从资源中提取的用户界面信息。
 */
public class ResourceUIInfo
        implements IUIInfo {

    private String displayName;
    private String description;
    private IIconMap iconMap;
    private PlainRefdocs helpIndex;

    public ResourceUIInfo(Class<?> clazz, Locale locale) {
        String baseName = clazz.getName();
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);

        this.displayName = getString(bundle, "_displayName", null);
        this.description = getString(bundle, "_description", null);
        this.iconMap = null;
        this.helpIndex = PlainRefdocs.parseResource(clazz, locale);
    }

    private String getString(ResourceBundle bundle, String key, String fallback) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return fallback;
        }
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public IIconMap getIconMap() {
        return iconMap;
    }

    @Override
    public IRefdocs getRefdocs() {
        return helpIndex;
    }

}
