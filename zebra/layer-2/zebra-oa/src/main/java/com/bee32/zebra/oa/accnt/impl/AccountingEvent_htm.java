package com.bee32.zebra.oa.accnt.impl;

import java.io.IOException;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.potato.PotatoTypes;
import net.bodz.bas.potato.element.IType;
import net.bodz.bas.repr.form.FormDeclBuilder;
import net.bodz.bas.repr.form.MutableFormDecl;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.accnt.AccountingEntry;
import com.bee32.zebra.oa.accnt.AccountingEvent;
import com.bee32.zebra.tk.hbin.ItemsTable;
import com.bee32.zebra.tk.slim.SlimPathFieldMap;
import com.bee32.zebra.tk.slim.SlimMesgForm_htm;

public class AccountingEvent_htm
        extends SlimMesgForm_htm<AccountingEvent> {

    public AccountingEvent_htm() {
        super(AccountingEvent.class);
    }

    @Override
    protected IHtmlOut afterForm(IHtmlViewContext ctx, IHtmlOut out, IUiRef<AccountingEvent> ref)
            throws ViewBuilderException, IOException {
        AccountingEvent acdoc = ref.get();
        if (acdoc.getId() == null)
            return out;

        SlimPathFieldMap itemIndexFields;
        {
            IType itemType = PotatoTypes.getInstance().forClass(AccountingEntry.class);
            FormDeclBuilder formDeclBuilder = new FormDeclBuilder();
            MutableFormDecl itemFormDecl;
            try {
                itemFormDecl = formDeclBuilder.build(itemType);
            } catch (ParseException e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }
            itemIndexFields = new SlimPathFieldMap(itemFormDecl);
            try {
                itemIndexFields.parse("i*", AccountingEntryIndex_htm.FIELDS);
            } catch (Exception e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }
        }

        Long id = acdoc.getId();

        ItemsTable itab = new ItemsTable(ctx, itemIndexFields.values());
        itab.editorUrl = _webApp_ + "acentry/ID/?view:=form&event.id=" + id;
        itab.ajaxUrl = "../../acentry/data.json?doc=" + id;
        HtmlTbody tbody = itab.buildViewStart(out, "entries");
        itab.buildViewEnd(tbody);
        return out;
    }

}
