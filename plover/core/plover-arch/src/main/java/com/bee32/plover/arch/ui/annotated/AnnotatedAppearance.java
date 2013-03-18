package com.bee32.plover.arch.ui.annotated;

import java.lang.reflect.AnnotatedElement;
import java.net.MalformedURLException;
import java.net.URL;

import javax.free.DisplayNameUtil;
import javax.free.DocUtil;
import javax.free.IllegalUsageException;

import com.bee32.plover.arch.ui.*;
import com.bee32.plover.arch.ui.res.ImageVariant;

public class AnnotatedAppearance
        extends LazyAppearance {

    private final AnnotatedElement element;
    private final URL contextURL;

    public AnnotatedAppearance(IAppearance parent, AnnotatedElement element, URL contextURL) {
        super(parent);

        if (element == null)
            throw new NullPointerException("element");

        if (contextURL == null)
            throw new NullPointerException("contextURL");

        this.element = element;
        this.contextURL = contextURL;
    }

    @Override
    public String loadLabel() {
        return DisplayNameUtil.getDisplayName(element);
    }

    @Override
    public String loadDescription() {
        return DocUtil.getDoc(element);
    }

    @Override
    public IImageMap loadImageMap() {
        Image[] _images = ImageMapUtil.getImages(element);
        if (_images == null)
            return null;

        SimpleImageMap imageMap = new SimpleImageMap();
        for (Image _image : _images) {
            String qualifier = _image.qualifier();
            int widthHint = _image.widthHint();
            int heightHint = _image.heightHint();

            String urlSpec = _image.url();
            URL url;
            try {
                url = new URL(contextURL, urlSpec);
            } catch (MalformedURLException e) {
                throw new IllegalUsageException(e.getMessage(), e);
            }

            ImageVariant variant = new ImageVariant(qualifier, widthHint, heightHint);
            imageMap.addImage(variant, url);
        }
        return imageMap;
    }

    @Override
    public IRefdocs loadRefdocs() {
        Refdoc[] _refdocs = RefdocsUtil.getRefdocs(element);
        if (_refdocs == null)
            return null;

        SimpleRefdocs refdocs = new SimpleRefdocs();
        if (_refdocs != null)
            for (Refdoc _refdoc : _refdocs) {
                String qualifier = _refdoc.qualifier();
                String title = _refdoc.title();
                String[] tags = _refdoc.tags();

                String urlSpec = _refdoc.url();
                URL url;
                try {
                    url = new URL(contextURL, urlSpec);
                } catch (MalformedURLException e) {
                    throw new IllegalUsageException(e.getMessage(), e);
                }

                RefdocEntry refdocEntry = new RefdocEntry(title, url, tags);
                refdocs.addRefdoc(qualifier, refdocEntry);
            }
        return refdocs;
    }

}
