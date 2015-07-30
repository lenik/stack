package com.bee32.zebra.oa.contact.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.contact.Contact;
import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.CriteriaBuilder;

public class OrganizationIndex_htm
        extends SlimIndex_htm<OrganizationIndex, Organization, OrganizationCriteria> {

    public OrganizationIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(OrganizationIndex.class);
        indexFields.parse("i*sa", "typeChars", "fullName", "size", "description", //
                "contact0.fullAddress", "contact0.tels", "contact0.qq");
    }

    @Override
    protected OrganizationCriteria buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        OrganizationCriteria criteria = CriteriaBuilder.fromRequest(new OrganizationCriteria(), ctx.getRequest());

        SwitcherModel<Integer> sw;
        sw = switchers.entryOf("类型", false, //
                PartyType.list, "type", criteria.type, false);
        criteria.type = sw.getSelection1();

        return criteria;
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<Organization> a, IUiRef<OrganizationIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        OrganizationMapper mapper = ctx.query(OrganizationMapper.class);
        OrganizationCriteria criteria = ctx.query(OrganizationCriteria.class);
        List<Organization> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (Organization o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                tr.td().text(o.getTypeChars());
                tr.td().b().text(o.getFullName());
                tr.td().text(o.getSize());
                tr.td().text(o.getDescription()).class_("small");

                Contact contact = o.getContact0();
                if (contact == null) {
                    for (int i = 0; i < 3; i++)
                        tr.td();
                } else {
                    tr.td().text(contact.getFullAddress()).class_("small");
                    tr.td().text(contact.getTels());
                    tr.td().text(contact.getQq());
                }
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
