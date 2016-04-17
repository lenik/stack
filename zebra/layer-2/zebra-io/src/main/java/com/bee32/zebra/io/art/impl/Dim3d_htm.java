package com.bee32.zebra.io.art.impl;

import java.io.IOException;

import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlInput;
import net.bodz.bas.html.util.FieldDeclToHtml;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.html.viz.builtin.AbstractFormInput_htm;
import net.bodz.bas.potato.ref.UiPropertyRef;
import net.bodz.bas.repr.form.IFieldDecl;
import net.bodz.bas.repr.viz.ViewBuilderException;

import com.bee32.zebra.io.art.Dim3d;

public class Dim3d_htm
        extends AbstractFormInput_htm<Dim3d> {

    public Dim3d_htm() {
        super(Dim3d.class);
    }

    @Override
    public void buildHtmlView(IHtmlViewContext ctx, IHtmlOut out, UiPropertyRef<Dim3d> ref, IFieldDecl fieldDecl)
            throws ViewBuilderException, IOException {

        Dim3d dim = ref.get();

        String str = dim.toString();
        HtmlInput input = out.input().type("text").class_("noprint").value(str);
        FieldDeclToHtml.apply(input, fieldDecl);

        out.span().class_("print").text(str);
    }

}
