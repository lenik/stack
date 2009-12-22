package com.seccaproject.plover.arch.ui;

import java.net.URL;
import java.util.Locale;

/**
 * @see AccessibleContextImpl
 */
public interface IUIInfo {

    String getDisplayName(Locale locale);

    String getDescription(Locale locale);

    URL getIcon(Locale locale, IconVariant variant);

    HelpIndex getHelpIndex();

}
