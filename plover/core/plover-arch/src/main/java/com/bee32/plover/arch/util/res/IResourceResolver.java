package com.bee32.plover.arch.util.res;

import java.net.MalformedURLException;
import java.net.URL;

public interface IResourceResolver {

    /**
     * Resolve given url spec.
     *
     * @param spec
     *            Non-<code>null</code> url spec.
     * @return Joined url of context and spec.
     */
    URL resolve(String spec)
            throws MalformedURLException;

}
