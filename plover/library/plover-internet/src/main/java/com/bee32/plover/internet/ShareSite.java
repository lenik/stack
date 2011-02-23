package com.bee32.plover.internet;

import java.net.URL;

import com.bee32.plover.arch.Component;

public abstract class ShareSite
        extends Component
        implements IShareSite {

    URL logoURL;

    public ShareSite(URL logoURL) {
        if (logoURL == null)
            throw new NullPointerException("logoURL");
        this.logoURL = logoURL;
    }

    public ShareSite(String name) {
        super(name);
    }

    @Override
    public URL getLogoURL() {
        return logoURL;
    }

}
