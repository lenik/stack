package com.bee32.zebra.oa.hr.impl;

import java.io.IOException;

import net.bodz.bas.html.AbstractHtmlViewBuilder;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.site.IBasicSiteAnchors;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.hr.JobSkillCategory;

public class JobSkillCategoryVbo
        extends AbstractHtmlViewBuilder<JobSkillCategory>
        implements IBasicSiteAnchors {
    
    public JobSkillCategoryVbo() {
        super(JobSkillCategory.class);
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<JobSkillCategory> ref, IOptions options)
            throws ViewBuilderException, IOException {
        return ctx;
    }

}
