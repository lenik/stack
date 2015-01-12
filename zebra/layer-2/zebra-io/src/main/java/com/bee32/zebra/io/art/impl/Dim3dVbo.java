package com.bee32.zebra.io.art.impl;

import java.io.IOException;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlInputTag;
import net.bodz.bas.html.util.FieldHtmlUtil;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.html.viz.builtin.AbstractFormInput_htm;
import net.bodz.bas.potato.ref.UiPropertyRef;
import net.bodz.bas.repr.form.IFieldDecl;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;

import com.bee32.zebra.io.art.Dim3d;

public class Dim3dVbo
        extends AbstractFormInput_htm<Dim3d> {

    public Dim3dVbo() {
        super(Dim3d.class);
    }

    @Override
    public IHtmlTag buildHtmlView(IHtmlViewContext ctx, IHtmlTag out, UiPropertyRef<Dim3d> ref, IFieldDecl fieldDecl,
            IOptions options)
            throws ViewBuilderException, IOException {

        Dim3d dim = ref.get();

        HtmlInputTag input = out.input().type("text").value(dim.toString());
        FieldHtmlUtil.apply(input, fieldDecl, options);

        return out;
    }

}