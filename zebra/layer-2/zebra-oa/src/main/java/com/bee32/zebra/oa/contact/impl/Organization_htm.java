package com.bee32.zebra.oa.contact.impl;

import java.io.IOException;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.potato.PotatoTypes;
import net.bodz.bas.potato.element.IType;
import net.bodz.bas.repr.form.FormDeclBuilder;
import net.bodz.bas.repr.form.MutableFormDecl;
import net.bodz.bas.repr.form.PathFieldMap;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.contact.Contact;
import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.tk.hbin.ItemsTable;
import com.bee32.zebra.tk.hbin.SectionDiv_htm1;
import com.bee32.zebra.tk.slim.SlimForm_htm;

public class Organization_htm
        extends SlimForm_htm<Organization> {

    public Organization_htm() {
        super(Organization.class);
    }

    @Override
    protected IHtmlOut afterForm(IHtmlViewContext ctx, IHtmlOut out, IUiRef<Organization> ref)
            throws ViewBuilderException, IOException {
        Organization org = ref.get();
        Integer id = org.getId();
        IHtmlOut sect;
        if (id != null) {
            sect = new SectionDiv_htm1("联系方式", IFontAwesomeCharAliases.FA_PHONE_SQUARE).build(out, "s-contact");
            buildContacts(ctx, sect, org);
        }
        return out;
    }

    void buildContacts(IHtmlViewContext ctx, IHtmlOut out, Organization org)
            throws ViewBuilderException, IOException {
        IType itemType = PotatoTypes.getInstance().forClass(Contact.class);
        FormDeclBuilder formDeclBuilder = new FormDeclBuilder();
        MutableFormDecl itemFormDecl;
        try {
            itemFormDecl = formDeclBuilder.build(itemType);
        } catch (ParseException e) {
            throw new ViewBuilderException(e.getMessage(), e);
        }

        PathFieldMap fields = new PathFieldMap(itemFormDecl);
        try {
            fields.parse("i*", ContactIndex_htm.FIELDS);
        } catch (Exception e) {
            throw new ViewBuilderException(e.getMessage(), e);
        }

        Integer id = org.getId();

        ItemsTable itab = new ItemsTable(ctx, fields.values());
        itab.editorUrl = _webApp_ + "contact/ID/?view:=form&org.id=" + id;
        itab.ajaxUrl = "../../contact/data.json?org=" + id;
        HtmlTbody tbody = itab.buildViewStart(out, "contacts");
        itab.buildViewEnd(tbody);
    }

}
