package com.bee32.plover.internet;

import java.net.URL;

import com.bee32.plover.arch.IComponent;
import com.bee32.plover.model.qualifier.IQualified;

public interface IShareSite
        extends IComponent, IQualified {

    URL getLogoURL();

    URL getClickShareURL(URL resource);

}
