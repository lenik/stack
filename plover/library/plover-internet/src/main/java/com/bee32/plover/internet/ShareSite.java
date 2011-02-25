package com.bee32.plover.internet;

import java.net.MalformedURLException;
import java.net.URL;

import javax.free.IllegalUsageException;

import com.bee32.plover.model.qualifier.Qualified;

public abstract class ShareSite
        extends Qualified
        implements IShareSite {

    URL logoURL;
    String urlFormat;

    public ShareSite(String urlFormat, String logoURL) {
        if (urlFormat == null)
            throw new NullPointerException("urlFormat");
        if (logoURL == null)
            throw new NullPointerException("logoURL");
        try {
            this.logoURL = new URL(logoURL);
        } catch (MalformedURLException e) {
            throw new IllegalUsageException("Bad logo URL", e);
        }
    }

    @Override
    public URL getLogoURL() {
        return logoURL;
    }

    @Override
    public URL getClickShareURL(URL resource) {
        String s = String.format(urlFormat, resource);
        try {
            return new URL(s);
        } catch (MalformedURLException e) {
            throw new IllegalUsageException("Bad share URL format", e);
        }
    }

}
