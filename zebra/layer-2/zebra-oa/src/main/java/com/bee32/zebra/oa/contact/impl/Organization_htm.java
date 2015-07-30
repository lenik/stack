package com.bee32.zebra.oa.contact.impl;

import java.io.IOException;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.potato.PotatoTypes;
import net.bodz.bas.potato.element.IType;
import net.bodz.bas.repr.form.FormDeclBuilder;
import net.bodz.bas.repr.form.MutableFormDecl;
import net.bodz.bas.repr.form.PathFieldMap;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.contact.Contact;
import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.tk.hbin.ItemsTable;
import com.bee32.zebra.tk.hbin.SectionDiv;
import com.bee32.zebra.tk.slim.SlimForm_htm;

public class Organization_htm
        extends SlimForm_htm<Organization> {

    public Organization_htm() {
        super(Organization.class);
    }

    @Override
    protected IHtmlTag afterForm(IHtmlViewContext ctx, IHtmlTag out, IUiRef<Organization> ref, IOptions options)
            throws ViewBuilderException, IOException {
        Organization org = ref.get();
        Integer id = org.getId();
        SectionDiv section;
        if (id != null) {
            section = new SectionDiv(out, "s-contact", "联系方式", IFontAwesomeCharAliases.FA_PHONE_SQUARE);
            buildContacts(ctx, section, org);
        }
        return out;
    }

    void buildContacts(IHtmlViewContext ctx, IHtmlTag out, Organization org)
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
        ItemsTable itab = new ItemsTable(out, "contacts", //
                _webApp_ + "contact/ID/?view:=form&org.id=" + id);
        itab.ajaxUrl = "../../contact/data.json?org=" + id;
        itab.buildHeader(fields.values());
    }

}
