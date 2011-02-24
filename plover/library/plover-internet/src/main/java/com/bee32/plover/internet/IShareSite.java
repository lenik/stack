package com.bee32.plover.internet;

import java.net.URL;

import com.bee32.plover.model.IQualifiedComponent;

public interface IShareSite
        extends IQualifiedComponent {

    URL getLogoURL();

    URL getClickShareURL(URL resource);

}
