package com.bee32.zebra.oa.hr.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.hr.JobSkillCategory;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class JobSkillCategoryManagerVbo
        extends Zc3Template_CEM<JobSkillCategoryManager, JobSkillCategory> {

    public JobSkillCategoryManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(JobSkillCategoryManager.class);
        formStruct = new JobSkillCategory().getFormStruct();
        insertIndexFields("code", "label", "description");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<JobSkillCategoryManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        JobSkillCategoryManager manager = ref.get();
        JobSkillCategoryMapper mapper = manager.getMapper();
        List<JobSkillCategory> list = mapper.all();

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (JobSkillCategory o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            stdcols0(tr, o);
            stdcols1(tr, o);
        }

        dumpData(p.extradata, list);

        return ctx;
    }

}
