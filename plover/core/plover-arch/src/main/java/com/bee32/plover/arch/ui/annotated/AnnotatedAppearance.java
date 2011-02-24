package com.bee32.plover.arch.ui.annotated;

import java.lang.reflect.AnnotatedElement;
import java.net.MalformedURLException;
import java.net.URL;

import javax.free.DisplayNameUtil;
import javax.free.DocUtil;

import com.bee32.plover.arch.ui.IAppearance;
import com.bee32.plover.arch.ui.IImageMap;
import com.bee32.plover.arch.ui.IRefdocs;
import com.bee32.plover.arch.ui.RefdocEntry;
import com.bee32.plover.arch.ui.SimpleImageMap;
import com.bee32.plover.arch.ui.SimpleRefdocs;
import com.bee32.plover.arch.ui.res.ImageVariant;

public class AnnotatedAppearance
        implements IAppearance {

    private final String displayName;
    private final String description;
    private final SimpleRefdocs refdocs;
    private final SimpleImageMap imageMap;

    public AnnotatedAppearance(AnnotatedElement element)
            throws MalformedURLException {
        displayName = DisplayNameUtil.getDisplayName(element);
        description = DocUtil.getDoc(element);

        refdocs = new SimpleRefdocs();
        Refdoc[] _refdocs = RefdocsUtil.getRefdocs(element);
        if (_refdocs != null)
            for (Refdoc refdoc : _refdocs) {
                String qualifier = refdoc.qualifier();
                String title = refdoc.title();
                String[] tags = refdoc.tags();

                String urlSpec = refdoc.url();
                URL url = new URL(urlSpec); // XXX - context/spec

                RefdocEntry refdocEntry = new RefdocEntry(title, url, tags);
                refdocs.addRefdoc(qualifier, refdocEntry);
            }

        imageMap = new SimpleImageMap();
        Image[] images = ImageMapUtil.getImages(element);
        if (images != null)
            for (Image image : images) {
                String qualifier = image.qualifier();
                int widthHint = image.widthHint();
                int heightHint = image.heightHint();

                String urlSpec = image.url();
                URL url = new URL(urlSpec); // XXX - context/spec

                ImageVariant variant = new ImageVariant(qualifier, widthHint, heightHint);
                imageMap.addImage(variant, url);
            }
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public IImageMap getImageMap() {
        return imageMap;
    }

    @Override
    public IRefdocs getRefdocs() {
        return refdocs;
    }

}
