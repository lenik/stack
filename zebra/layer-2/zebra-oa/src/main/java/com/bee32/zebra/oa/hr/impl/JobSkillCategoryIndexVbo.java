package com.bee32.zebra.oa.hr.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.hr.JobSkillCategory;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

public class JobSkillCategoryIndexVbo
        extends SlimIndex_htm<JobSkillCategoryIndex, JobSkillCategory, JobSkillCategoryCriteria> {

    public JobSkillCategoryIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(JobSkillCategoryIndex.class);
        indexFields.parse("i*sa", "code", "label", "description");
    }

    @Override
    protected JobSkillCategoryCriteria buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        JobSkillCategoryCriteria criteria = fn.criteriaFromRequest(new JobSkillCategoryCriteria(), ctx.getRequest());
        return criteria;
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<JobSkillCategory> a,
            IUiRef<JobSkillCategoryIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        JobSkillCategoryMapper mapper = ctx.query(JobSkillCategoryMapper.class);
        JobSkillCategoryCriteria criteria = ctx.query(JobSkillCategoryCriteria.class);
        List<JobSkillCategory> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (JobSkillCategory o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
