package com.bee32.plover.view;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import com.bee32.plover.util.Mime;

public class FormatManager {

    private Map<Mime, ContentFormat> formats;

    public FormatManager() {
        formats = new HashMap<Mime, ContentFormat>();
    }

    public ContentFormat getOrCreateFormat(Mime contentType) {
        ContentFormat contentFormat = formats.get(contentType);
        if (contentFormat == null) {
            synchronized (formats) {
                contentFormat = formats.get(contentType);
                if (contentFormat == null) {
                    contentFormat = new ContentFormat(contentType);
                    formats.put(contentType, contentFormat);
                }
            }
        }
        return contentFormat;
    }

    public void addRenderer(IContentRenderer contentRenderer) {
        for (Mime contentType : contentRenderer.getSupportedContentType()) {
            ContentFormat contentFormat = getOrCreateFormat(contentType);
            contentFormat.addRenderer(contentRenderer);
        }
    }

    public void removeRenderer(IContentRenderer contentRenderer) {
        for (Mime contentType : contentRenderer.getSupportedContentType()) {
            ContentFormat contentFormat = getOrCreateFormat(contentType);
            contentFormat.removeRenderer(contentRenderer);
        }
    }

    public IContentRenderer findRenderer(Object obj, Mime contentType) {
        ContentFormat contentFormat = formats.get(contentType);
        if (contentFormat == null)
            return null;
        return contentFormat.getRenderer(obj);
    }

    private static final FormatManager instance = new FormatManager();
    static {
        for (IContentRenderer contentRenderer : ServiceLoader.load(IContentRenderer.class)) {
            instance.addRenderer(contentRenderer);
        }
    }

    public static FormatManager getInstance() {
        return instance;
    }

}
