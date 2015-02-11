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
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

public class ContactIndexVbo
        extends SlimIndex_htm<ContactIndex, Contact, ContactCriteria> {

    public static final String[] FIELDS = { "priority", "rename", "usage",
            // "region",
            "tel", "mobile", "fax", "email", "web", "qq", "postalCode", "address1", "address2" };

    public ContactIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(ContactIndex.class);
        indexFields.parse("i*sa", FIELDS);
    }

    @Override
    protected ContactCriteria buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        ContactCriteria criteria = fn.criteriaFromRequest(new ContactCriteria(), ctx.getRequest());
        return criteria;
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<Contact> a, IUiRef<ContactIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        ContactMapper mapper = ctx.query(ContactMapper.class);
        ContactCriteria criteria = ctx.query(ContactCriteria.class);
        List<Contact> list = postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (Contact o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                ref(tr.td(), o.getOrg());
                ref(tr.td(), o.getOrgUnit());
                ref(tr.td(), o.getPerson());
                tr.td().text(o.getRename());
                tr.td().text(o.getUsage());
                // tr.td().text(o.getRegion());
                tr.td().text(o.getCountry());
                tr.td().text(o.getR1());
                tr.td().text(o.getR2());
                tr.td().text(o.getR3());
                tr.td().text(o.getAddress1());
                tr.td().text(o.getAddress2());
                tr.td().text(o.getTel());
                tr.td().text(o.getMobile());
                tr.td().text(o.getFax());
                tr.td().text(o.getEmail());
                tr.td().text(o.getWeb());
                tr.td().text(o.getQq());
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
