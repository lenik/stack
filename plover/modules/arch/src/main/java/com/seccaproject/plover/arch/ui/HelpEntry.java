package com.seccaproject.plover.arch.ui;

import java.net.URL;

public class HelpEntry
        implements IHelpEntry {

    private URL url;
    private String[] tags;
    private String title;

    public HelpEntry(URL url, String... tags) {
        this(null, url, tags);
    }

    public HelpEntry(String title, URL url, String... tags) {
        if (url == null)
            throw new NullPointerException("url");
        if (tags == null)
            throw new NullPointerException("tags");
        this.title = title;
        this.url = url;
        this.tags = tags;
    }

    @Override
    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        if (tags == null)
            throw new NullPointerException("tags");
        this.tags = tags;
    }

    @Override
    public URL getURL() {
        return url;
    }

    public void setURL(URL url) {
        if (url == null)
            throw new NullPointerException("url");
        this.url = url;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title + " (" + url + ")";
    }

}
