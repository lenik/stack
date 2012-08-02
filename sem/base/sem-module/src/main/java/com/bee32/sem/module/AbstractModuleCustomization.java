package com.bee32.sem.module;

import java.util.Map;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.arch.ModuleManager;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.html.SimpleForm;
import com.bee32.plover.servlet.util.HttpAssembledContext;
import com.bee32.plover.site.SiteInstance;
import com.bee32.plover.site.cfg.AbstractSiteConfigBlock;
import com.bee32.sem.term.ITermProvider;
import com.bee32.sem.term.TermMetadata;

public abstract class AbstractModuleCustomization
        extends AbstractSiteConfigBlock {

    IModule module;
    String moduleName;
    String moduleTitle;

    protected static class ctx
            extends HttpAssembledContext {
    }

    public AbstractModuleCustomization(Class<? extends IEnterpriseModule> moduleClass) {
        this(ModuleManager.getInstance().getModule(moduleClass));
    }

    public AbstractModuleCustomization(IModule module) {
        if (module == null)
            throw new NullPointerException("module");
        this.module = module;
        this.moduleName = module.getName();
        this.moduleTitle = module.getAppearance().getDisplayName();
    }

    @Override
    public void configForm(SiteInstance site, SimpleForm form) {
        form.section(moduleName, moduleTitle);

        ITermProvider termProvider = getTermProvider();
        Map<String, TermMetadata> termMap = termProvider.getTermMap();
        for (TermMetadata term : termMap.values()) {
            String inputName = moduleName + ".term." + term.getName();
            String labelTip = "术语 " + term.getLabel() + " 重写为:" + term.getDescription();
            String termLabel = termProvider.getTermLabel(site, term.getName());
            form.addEntry(inputName, labelTip, termLabel);
        }
    }

    @Override
    public void saveForm(SiteInstance site, TextMap args) {
        ITermProvider termProvider = getTermProvider();
        Map<String, TermMetadata> termMap = termProvider.getTermMap();
        for (TermMetadata metadata : termMap.values()) {
            String inputName = moduleName + ".term." + metadata.getName();

            String termLabel = args.getString(inputName);
            if (termLabel != null) {
                termLabel = termLabel.trim();
                if (termLabel.isEmpty())
                    termLabel = null;
                else {
                    String _label = metadata.getLabel();
                    if (termLabel.equals(_label))
                        termLabel = null;
                }
            }

            termProvider.setTermLabel(site, metadata.getName(), termLabel);
        }
    }

    public abstract ITermProvider getTermProvider();

}
