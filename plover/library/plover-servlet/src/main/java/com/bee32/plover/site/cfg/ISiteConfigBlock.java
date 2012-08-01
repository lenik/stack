package com.bee32.plover.site.cfg;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.html.SimpleForm;
import com.bee32.plover.site.SiteInstance;

public interface ISiteConfigBlock {

    void configForm(SiteInstance site, SimpleForm form);

    void saveForm(SiteInstance site, TextMap args);

}
