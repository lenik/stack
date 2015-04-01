package com.bee32.zebra.oa.accnt.impl;

import java.io.IOException;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.potato.PotatoTypes;
import net.bodz.bas.potato.element.IType;
import net.bodz.bas.repr.form.FormDeclBuilder;
import net.bodz.bas.repr.form.MutableFormDecl;
import net.bodz.bas.repr.form.PathFieldMap;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.accnt.AccountingEntry;
import com.bee32.zebra.oa.accnt.AccountingEvent;
import com.bee32.zebra.tk.hbin.ItemsTable;
import com.bee32.zebra.tk.slim.SlimMesgForm_htm;

public class AccountingEventVbo
        extends SlimMesgForm_htm<AccountingEvent> {

    public AccountingEventVbo() {
        super(AccountingEvent.class);
    }

    @Override
    protected IHtmlTag afterForm(IHttpViewContext ctx, IHtmlTag out, IUiRef<AccountingEvent> ref, IOptions options)
            throws ViewBuilderException, IOException {
        AccountingEvent acdoc = ref.get();
        if (acdoc.getId() == null)
            return out;

        PathFieldMap itemIndexFields;
        {
            IType itemType = PotatoTypes.getInstance().forClass(AccountingEntry.class);
            FormDeclBuilder formDeclBuilder = new FormDeclBuilder();
            MutableFormDecl itemFormDecl;
            try {
                itemFormDecl = formDeclBuilder.build(itemType);
            } catch (ParseException e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }
            itemIndexFields = new PathFieldMap(itemFormDecl);
            try {
                itemIndexFields.parse("i*", AccountingEntryIndexVbo.FIELDS);
            } catch (Exception e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }
        }

        Long id = acdoc.getId();
        ItemsTable itab = new ItemsTable(out, "entries", //
                _webApp_ + "acentry/ID/?view:=form&event.id=" + id);
        itab.ajaxUrl = "../../acentry/data.json?doc=" + id;
        itab.buildHeader(itemIndexFields.values());
        return out;
    }
}
