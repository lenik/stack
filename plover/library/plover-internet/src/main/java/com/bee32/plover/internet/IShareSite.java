package com.bee32.plover.internet;

import java.net.URL;

import com.bee32.plover.arch.IComponent;
import com.bee32.plover.arch.extension.IExtensionPoint;

public interface IShareSite
        extends IComponent, IExtensionPoint {

    URL getLogoURL();

    URL getClickShareURL(URL resource);

}
