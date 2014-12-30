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

import com.bee32.zebra.oa.hr.EmployeeSkill;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class EmployeeSkillManagerVbo
        extends Zc3Template_CEM<EmployeeSkillManager, EmployeeSkill> {

    public EmployeeSkillManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(EmployeeSkillManager.class);
        formDef = new EmployeeSkill().getFormDef();
        insertIndexFields("i*sa", "label", "description");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<EmployeeSkillManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        EmployeeSkillMapper mapper = ctx.query(EmployeeSkillMapper.class);
        List<EmployeeSkill> list = postfilt(mapper.all());

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (EmployeeSkill o : list) {

            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            cocols("sa", tr, o);
        }

        dumpFullData(p.extradata, list);

        return ctx;
    }

}
