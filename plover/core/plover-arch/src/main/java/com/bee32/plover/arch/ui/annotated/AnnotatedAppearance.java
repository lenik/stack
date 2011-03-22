package com.bee32.plover.arch.ui.annotated;

import java.lang.reflect.AnnotatedElement;
import java.net.MalformedURLException;
import java.net.URL;

import javax.free.DisplayNameUtil;
import javax.free.DocUtil;
import javax.free.IllegalUsageException;

import com.bee32.plover.arch.ui.Appearance;
import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.arch.ui.RefdocEntry;
import com.bee32.plover.arch.ui.SimpleImageMap;
import com.bee32.plover.arch.ui.SimpleRefdocs;
import com.bee32.plover.arch.ui.res.ImageVariant;

public class AnnotatedAppearance
        extends Appearance {

    private final AnnotatedElement element;

    public AnnotatedAppearance(IAppearance parent, AnnotatedElement element) {
        super(parent);
        this.element = element;
    }

    @Override
    public String loadDisplayName() {
        return DisplayNameUtil.getDisplayName(element);
    }

    @Override
    public String loadDescription() {
        return DocUtil.getDoc(element);
    }

    @Override
    public SimpleRefdocs loadRefdocs() {
        SimpleRefdocs refdocs = new SimpleRefdocs();
        Refdoc[] _refdocs = RefdocsUtil.getRefdocs(element);
        if (_refdocs != null)
            for (Refdoc _refdoc : _refdocs) {
                String qualifier = _refdoc.qualifier();
                String title = _refdoc.title();
                String[] tags = _refdoc.tags();

                String urlSpec = _refdoc.url();
                URL url;
                try {
                    // XXX - context/spec
                    url = new URL(urlSpec);
                } catch (MalformedURLException e) {
                    throw new IllegalUsageException(e.getMessage(), e);
                }

                RefdocEntry refdocEntry = new RefdocEntry(title, url, tags);
                refdocs.addRefdoc(qualifier, refdocEntry);
            }
        return refdocs;
    }

    @Override
    public SimpleImageMap loadImageMap() {
        SimpleImageMap imageMap = new SimpleImageMap();
        Image[] _images = ImageMapUtil.getImages(element);
        if (_images != null)
            for (Image _image : _images) {
                String qualifier = _image.qualifier();
                int widthHint = _image.widthHint();
                int heightHint = _image.heightHint();

                String urlSpec = _image.url();
                URL url;
                try {
                    // XXX - context/spec
                    url = new URL(urlSpec);
                } catch (MalformedURLException e) {
                    throw new IllegalUsageException(e.getMessage(), e);
                }

                ImageVariant variant = new ImageVariant(qualifier, widthHint, heightHint);
                imageMap.addImage(variant, url);
            }
        return imageMap;
    }

}
