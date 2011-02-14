package com.bee32.plover.conf;

import javax.free.IContext;

/**
 * Theme style is provided by system.
 *
 * This is a marker-class.
 */
public abstract class ThemeStyle
        extends Style {

    public ThemeStyle(String styleName, IContext parentContext) {
        super(styleName, parentContext);
    }

}
