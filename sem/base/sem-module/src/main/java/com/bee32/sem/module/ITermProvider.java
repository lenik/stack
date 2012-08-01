package com.bee32.sem.module;

import java.util.Map;

import com.bee32.plover.site.SiteInstance;

public interface ITermProvider {

    Map<String, TermMetadata> getTermMap();

    TermMetadata getTermMetadata(String termName);

    String getTermLabel(SiteInstance site, String termName);

    void setTermLabel(SiteInstance site, String termName, String termLabel);

    String getTermDescription(SiteInstance site, String termName);

    void setTermDescription(SiteInstance site, String termName, String termDescription);

}
