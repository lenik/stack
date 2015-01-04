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
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class OrganizationManagerVbo
        extends Zc3Template_CEM<OrganizationManager, Organization> {

    public OrganizationManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(OrganizationManager.class);
        insertIndexFields("i*sa", "typeChars", "fullName", "size", "description", //
                "contact.fullAddress", "contact.tels", "contact.qq");
    }

    @Override
    protected void buildDataView(IHtmlViewContext ctx, PageStruct page, IUiRef<OrganizationManager> ref, IOptions options)
            throws ViewBuilderException, IOException {
        OrganizationMapper mapper = ctx.query(OrganizationMapper.class);
        List<Organization> list = postfilt(mapper.all());

        IndexTable indexTable = mkIndexTable(ctx, page.mainCol, "list");

        for (Organization o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            tr.td().text(o.getTypeChars());
            tr.td().b().text(o.getFullName());
            tr.td().text(o.getSize());
            tr.td().text(o.getDescription()).class_("small");

            Contact contact = o.getContact();
            if (contact == null) {
                for (int i = 0; i < 3; i++)
                    tr.td();
            } else {
                tr.td().text(contact.getFullAddress()).class_("small");
                tr.td().text(contact.getTels());
                tr.td().text(contact.getQq());
            }
            cocols("sa", tr, o);
        }

        dumpFullData(page.extradata, list);
    }

}
