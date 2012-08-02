package com.bee32.sem.term;

import java.util.Map;

import javax.free.IllegalUsageException;

import com.bee32.plover.inject.ServiceTemplate;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.SiteInstance;
import com.bee32.plover.site.cfg.SitePropertyPrefix;

/**
 * You should annotate with &#64;{@link SitePropertyPrefix} in concrete implementations.
 */
@ServiceTemplate
public abstract class AbstractModuleTerms
        implements ITermProvider {

    String propertyPrefix;

    public AbstractModuleTerms() {
        SitePropertyPrefix _sitePropertyPrefix = getClass().getAnnotation(SitePropertyPrefix.class);
        if (_sitePropertyPrefix == null)
            throw new IllegalUsageException("You didn't annotate the " + getClass() + " with annotation "
                    + SitePropertyPrefix.class.getName());
        String prefix = _sitePropertyPrefix.value();
        if (!prefix.endsWith("."))
            prefix += ".";
        this.propertyPrefix = prefix;
    }

    @Override
    public Map<String, TermMetadata> getTermMap() {
        return TermMetadata.getMetadataMap(getClass());
    }

    @Override
    public TermMetadata getTermMetadata(String termName) {
        TermMetadata metadata = TermMetadata.fromTermProperty(getClass(), termName);
        return metadata;
    }

    @Override
    public String getTermLabel(SiteInstance site, String termName) {
        String propertyName = propertyPrefix + termName + ".label";
        String label = site.getProperty(propertyName);
        if (label == null)
            label = getTermMetadata(termName).getLabel();
        return label;
    }

    @Override
    public void setTermLabel(SiteInstance site, String termName, String termLabel) {
        String propertyName = propertyPrefix + termName + ".label";
        site.setProperty(propertyName, termLabel);
    }

    @Override
    public String getTermDescription(SiteInstance site, String termName) {
        String propertyName = propertyPrefix + termName + ".description";
        String description = site.getProperty(propertyName);
        if (description == null)
            description = getTermMetadata(termName).getDescription();
        return description;
    }

    @Override
    public void setTermDescription(SiteInstance site, String termName, String termDescription) {
        String propertyName = propertyPrefix + termName + ".description";
        site.setProperty(propertyName, termDescription);
    }

    public String getTermLabel(String termName) {
        SiteInstance site = ThreadHttpContext.getSiteInstance();
        return getTermLabel(site, termName);
    }

    public String getTermDescription(String termName) {
        SiteInstance site = ThreadHttpContext.getSiteInstance();
        return getTermDescription(site, termName);
    }

}
