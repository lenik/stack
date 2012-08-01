package com.bee32.plover.site;

import java.util.LinkedHashSet;
import java.util.Set;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.html.SimpleForm;
import com.bee32.plover.site.cfg.AbstractSiteConfigBlock;
import com.bee32.plover.site.cfg.MultiProfileSelection;

public class ProfileConfigBlock
        extends AbstractSiteConfigBlock {

    @Override
    public void configForm(SiteInstance site, SimpleForm form) {
        form.section("apps", "Application Customization");
        form.addEntry("profiles", "应用剪裁:选择要启用的功能、特性", //
                new MultiProfileSelection(site.getProfileNames()));
    }

    @Override
    public void saveForm(SiteInstance site, TextMap args) {
        String[] _profiles = args.getStringArray("profiles");

        Set<String> profileNames = new LinkedHashSet<String>();
        for (String profileName : _profiles)
            profileNames.add(profileName);

        site.setProfileNames(profileNames);
    }

}
