package com.bee32.sem.inventory;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.html.SimpleForm;
import com.bee32.plover.site.SiteInstance;
import com.bee32.sem.module.AbstractModuleCustomization;
import com.bee32.sem.term.ITermProvider;

public class SEMInventoryCustomization
        extends AbstractModuleCustomization {

    public static final String AUTO_BATCH_KEY = qualify("inventory", "conf", "autoBatch");

    public SEMInventoryCustomization() {
        super(SEMInventoryModule.class);
    }

    @Override
    public ITermProvider getTermProvider() {
        return new SEMInventoryTerms();
    }

    @Override
    public void configForm(SiteInstance site, SimpleForm form) {
        super.configForm(site, form);

        Boolean autoBatch = site.getBooleanProperty(AUTO_BATCH_KEY);
        form.addEntry(AUTO_BATCH_KEY, "自动使用当前日期作为批号", autoBatch);
    }

    @Override
    public void saveForm(SiteInstance site, TextMap args) {
        super.saveForm(site, args);

        Boolean autoBatch = args.getBoolean(AUTO_BATCH_KEY);
        site.setBooleanProperty(AUTO_BATCH_KEY, autoBatch);
    }

}
