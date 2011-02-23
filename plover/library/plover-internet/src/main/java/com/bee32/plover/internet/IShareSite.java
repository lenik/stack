package com.bee32.plover.internet;

import java.net.URL;

import com.bee32.plover.arch.IComponent;

public interface IShareSite
        extends IComponent {

    URL getLogoURL();

    URL getClickShareURL(URL resource);

}
