package com.seccaproject.plover.arch.ui;

import java.net.URL;

/**
 * @see AccessibleContextImpl
 */
public interface IUIInfo {
//
// String getDisplayName(Locale locale);
//
// String getDescription(Locale locale);
//
// URL getIcon(Locale locale, IconVariant variant);

    String getDisplayName();

    String getDescription();

    URL getIcon(IconVariant variant);

    IHelpIndex getHelpIndex();

}
