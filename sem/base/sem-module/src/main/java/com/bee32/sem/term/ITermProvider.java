package com.bee32.sem.term;

import java.util.Map;

import com.bee32.plover.site.SiteInstance;

public interface ITermProvider {

    Map<String, TermMetadata> getTermMap();

    TermMetadata getTermMetadata(String termName);

    /**
     * Get customizable term label.
     *
     * @return <code>null</code> only if the term is not defined. If the term is not overrided, the
     *         default non-<code>null</code> label should be returned.
     */
    String getTermLabel(SiteInstance site, String termName);

    /**
     * Override the label of specified term.
     */
    void setTermLabel(SiteInstance site, String termName, String termLabel);

    /**
     * Get customizable term description.
     *
     * @return <code>null</code> only if the term is not defined. If the term is not overrided, the
     *         default non-<code>null</code> description should be returned.
     */
    String getTermDescription(SiteInstance site, String termName);

    /**
     * Override the description of specified term.
     */
    void setTermDescription(SiteInstance site, String termName, String termDescription);

}
