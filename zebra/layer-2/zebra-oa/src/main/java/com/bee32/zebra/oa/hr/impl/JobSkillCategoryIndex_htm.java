package com.bee32.zebra.oa.hr.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.io.tag.HtmlTr;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.t.variant.VarMapState;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.CoObject;

import com.bee32.zebra.oa.hr.JobSkillCategory;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

public class JobSkillCategoryIndex_htm
        extends SlimIndex_htm<JobSkillCategoryIndex, JobSkillCategory, JobSkillCategoryMask> {

    public JobSkillCategoryIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(JobSkillCategoryIndex.class);
        indexFields.parse("i*sa", "code", "label", "description");
    }

    @Override
    protected JobSkillCategoryMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        JobSkillCategoryMask mask = VarMapState.restoreFrom(new JobSkillCategoryMask(), ctx.getRequest());
        return mask;
    }

    @Override
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<JobSkillCategoryIndex> ref)
            throws ViewBuilderException, IOException {
        JobSkillCategoryMapper mapper = ctx.query(JobSkillCategoryMapper.class);
        JobSkillCategoryMask mask = ctx.query(JobSkillCategoryMask.class);
        List<JobSkillCategory> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (JobSkillCategory o : list) {
            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);
            itab.cocols("sa", tr, o);
        }
        itab.buildViewEnd(tbody);
        return list;
    }

}
