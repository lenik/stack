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

        HtmlInputTag dx = out.input().type("number").value(dim.dx + "");
        out.text(" x ");
        HtmlInputTag dy = out.input().type("number").value(dim.dy + "");
        out.text(" x ");
        HtmlInputTag dz = out.input().type("number").value(dim.dz + "");

        FieldHtmlUtil.apply(dx, fieldDecl, options, "_dx");
        FieldHtmlUtil.apply(dy, fieldDecl, options, "_dy");
        FieldHtmlUtil.apply(dz, fieldDecl, options, "_dz");

        return out;
    }

}
