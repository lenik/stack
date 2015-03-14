package com.bee32.zebra.oa.calendar.impl;

import java.io.IOException;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.html.viz.util.AbstractForm_htm;
import net.bodz.bas.repr.form.FieldCategory;
import net.bodz.bas.repr.form.IFieldDecl;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.calendar.CalendarForm;

public class CalendarForm_htm
        extends AbstractForm_htm<CalendarForm>
        implements IFontAwesomeCharAliases {

    public CalendarForm_htm() {
        super(CalendarForm.class);
    }

    @Override
    protected void nullInstance(IHtmlTag out, IUiRef<CalendarForm> ref)
            throws ViewBuilderException, IOException {
    }

    @Override
    protected IHtmlTag beginCategory(IHttpViewContext ctx, IHtmlTag out, FieldCategory category)
            throws ViewBuilderException, IOException {
        return null;
    }

    @Override
    protected IHtmlTag beginField(IHttpViewContext ctx, IHtmlTag out, IFieldDecl fieldDecl)
            throws ViewBuilderException, IOException {
        return null;
    }

    @Override
    protected void fieldBody(IHttpViewContext ctx, IHtmlTag out, IUiRef<?> instanceRef, IFieldDecl fieldDecl,
            IOptions options)
            throws ViewBuilderException, IOException {
    }

    @Override
    protected void endField(IHttpViewContext ctx, IHtmlTag out, IHtmlTag fieldOut, IFieldDecl fieldDecl)
            throws ViewBuilderException, IOException {
    }

    @Override
    protected void endCategory(IHttpViewContext ctx, IHtmlTag out, IHtmlTag catOut, FieldCategory category) {
    }

    @Override
    public IHtmlTag buildHtmlView(IHttpViewContext ctx, IHtmlTag out, IUiRef<CalendarForm> ref, IOptions options)
            throws ViewBuilderException, IOException {
        if (enter(ctx))
            return null;

        return out;
    }

}
