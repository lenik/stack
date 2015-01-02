package com.bee32.zebra.tk.sea;

import java.io.IOException;

import net.bodz.bas.err.IllegalConfigException;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlH2Tag;
import net.bodz.bas.html.dom.tag.HtmlInputTag;
import net.bodz.bas.html.dom.tag.HtmlSpanTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHtmlViewBuilderFactory;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.html.viz.util.AbstractForm_htm;
import net.bodz.bas.potato.element.IProperty;
import net.bodz.bas.potato.ref.UiPropertyRef;
import net.bodz.bas.repr.form.FieldCategory;
import net.bodz.bas.repr.form.IFieldDecl;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.mda.xjdoc.model.javadoc.IXjdocElement;

import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.site.PageStruct;
import com.tinylily.model.base.CoEntity;

public abstract class FooVbo<T extends CoEntity>
        extends AbstractForm_htm<T>
        implements IZebraSiteAnchors, IFontAwesomeCharAliases {

    public FooVbo(Class<?> valueClass, String... supportedFeatures) {
        super(valueClass, supportedFeatures);
    }

    @Override
    public IHtmlTag buildHtmlView(IHtmlViewContext ctx, IHtmlTag out, IUiRef<T> ref, IOptions options)
            throws ViewBuilderException, IOException {
        return super.buildHtmlView(ctx, new PageStruct(ctx).mainCol, ref, options);
    }

    @Override
    protected void nullInstance(IHtmlTag out, IUiRef<T> ref) {
        out.text("null@" + ref.getValueType());
    }

    @Override
    protected IHtmlTag beginCategory(IHtmlTag out, FieldCategory category)
            throws ViewBuilderException {
        HtmlH2Tag h2 = out.h2();
        h2.span().class_("icon fa").text(FA_CUBE);
        if (category == FieldCategory.NULL)
            h2.text("基本信息");
        else
            h2.text(IXjdocElement.fn.labelName(category));

        return out.div().class_("container");
    }

    @Override
    protected void endCategory(IHtmlTag out, FieldCategory category) {
        out.hr();
    }

    @Override
    protected IHtmlTag beginField(IHtmlTag out, IFieldDecl fieldDecl)
            throws ViewBuilderException {
        HtmlDivTag row = out.div().class_("container-row");

        HtmlDivTag labelDiv = row.div().class_("col-sm-3 col-lg-2 zu-flabel");
        String labelName = IXjdocElement.fn.labelName(fieldDecl);
        labelDiv.text(labelName + ":");

        HtmlDivTag valueDiv = row.div().class_("col-sm-9 col-lg-10");
        return valueDiv;
    }

    @Override
    protected void fieldBody(IHtmlViewContext ctx, IHtmlTag out, IUiRef<T> instanceRef, IFieldDecl fieldDecl,
            IOptions options)
            throws ViewBuilderException, IOException {
        IProperty property = fieldDecl.getProperty();
        UiPropertyRef<Object> propertyRef = new UiPropertyRef<Object>(instanceRef, property);
        T instance = instanceRef.get();
        Object value = propertyRef.get();

        Class<?> type = property.getPropertyType();
        if (CoEntity.class.isAssignableFrom(type)) {
            CoEntity entity = (CoEntity) value;

            String inputName = fieldDecl.getInputName();
            if (inputName == null)
                inputName = fieldDecl.getName();

            HtmlInputTag input = out.input().type("hidden").name(inputName);
            if (entity != null)
                input.value(entity.getId().toString());

            HtmlSpanTag span = out.span().id(inputName + "-span");
            if (instance != null)
                span.text(instance.getLabel());

            out.a().href("javascript: alert(1)").text("选择");
            return;
        }

        IHtmlViewBuilderFactory factory = ctx.query(IHtmlViewBuilderFactory.class);
        if (factory == null)
            throw new IllegalConfigException(IHtmlViewBuilderFactory.class + " isn't set.");
        else
            factory.buildView(ctx, out, propertyRef);
    }

    @Override
    protected void endField(IHtmlTag out, IFieldDecl fieldDecl) {
    }

}
