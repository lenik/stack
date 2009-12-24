package com.seccaproject.plover.arch.ui;

import java.net.URL;

/**
 * 帮助、参考的信息条目
 */
public interface IRefdocEntry {

    // String getTitle(Locale locale);
    // URL getURL(Locale locale);

    /**
     * @return non-<code>null</code> array of tag names
     */
    String[] getTags();

    /**
     * @return <code>null</code> if title information isn't available.
     */
    String getTitle();

    /**
     * @return non-<code>null</code> {@link URL}, if there's no resource available for corresponding
     *         <code>locale</code>, a resource in default locale should be returned.
     */
    URL getURL();

}
