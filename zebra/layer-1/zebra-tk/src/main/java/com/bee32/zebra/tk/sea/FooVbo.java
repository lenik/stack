package com.bee32.zebra.tk.sea;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.bodz.bas.c.type.SingletonUtil;
import net.bodz.bas.err.IllegalConfigException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlButtonTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlFormTag;
import net.bodz.bas.html.dom.tag.HtmlInputTag;
import net.bodz.bas.html.dom.tag.HtmlSpanTag;
import net.bodz.bas.html.meta.HtmlViewBuilder;
import net.bodz.bas.html.util.FieldHtmlUtil;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHtmlViewBuilder;
import net.bodz.bas.html.viz.IHtmlViewBuilderFactory;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.html.viz.util.AbstractForm_htm;
import net.bodz.bas.potato.element.IProperty;
import net.bodz.bas.potato.ref.UiPropertyRef;
import net.bodz.bas.repr.form.FieldCategory;
import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.form.FieldDeclLabelComparator;
import net.bodz.bas.repr.form.IFieldDecl;
import net.bodz.bas.repr.req.IMethodOfRequest;
import net.bodz.bas.repr.req.MethodNames;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.mda.xjdoc.model.javadoc.IXjdocElement;

import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.site.IZebraSiteLayout.ID;
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

        IMethodOfRequest methodOfRequest = ctx.query(IMethodOfRequest.class);
        String methodName = methodOfRequest.getMethodName();
        switch (methodName) {
        case MethodNames.CREATE:
        case MethodNames.UPDATE:
            T data = ref.get();
            try {
                data.populate(ctx.getRequest().getParameterMap());
            } catch (ParseException e) {
                throw new ViewBuilderException(e.getMessage(), e);
            }
            data.persist(ctx, out);
            return null;

        case MethodNames.READ:
        case MethodNames.EDIT:
        default:
            break;
        }

        return super.buildHtmlView(ctx, new PageStruct(ctx).mainCol, ref, options);
    }

    @Override
    protected void nullInstance(IHtmlTag out, IUiRef<T> ref) {
        out.text("null@" + ref.getValueType());
    }

    @Override
    protected HtmlFormTag beginForm(IHtmlViewContext ctx, IHtmlTag out, IUiRef<T> ref, IOptions options)
            throws ViewBuilderException, IOException {
        HtmlFormTag form = out.form().name("form").method("post");
        form.div().id(ID.formtop);

        T entity = ref.get();
        Number id = (Number) entity.getId();
        boolean creation = id == null || id.intValue() == 0;
        HtmlInputTag methodParam = form.input().type("hidden").name("m:");
        if (creation)
            methodParam.value(MethodNames.CREATE);
        else
            methodParam.value(MethodNames.UPDATE);

        return form;
    }

    @Override
    protected void endForm(IHtmlViewContext ctx, IHtmlTag out, IUiRef<T> ref, IOptions options)
            throws ViewBuilderException, IOException {
        // out.hr();
        HtmlDivTag div = out.div();
        HtmlButtonTag submitButton = div.button().type("submit");
        submitButton.span().class_("fa icon").text(FA_ANGLE_DOUBLE_UP);
        submitButton.text("保存以上信息");

        HtmlButtonTag resetButton = div.button();
        resetButton.onclick("javascript: history.go(-1)");
        resetButton.text("取消编辑");
    }

    @Override
    protected boolean buildFieldGroup(IHtmlViewContext ctx, IHtmlTag out, IUiRef<T> instanceRef, FieldDeclGroup group,
            IOptions options)
            throws ViewBuilderException, IOException {
        String simpleName = group.getCategory().getTagClass().getSimpleName();
        switch (simpleName) {
        case "Object":
            return buildBasicGroup(ctx, out, instanceRef, group, options);
        case "Content":
        case "Ranking":
        case "Metadata":
        case "Security":
            return true;
        }
        return false;
    }

    protected boolean buildBasicGroup(IHtmlViewContext ctx, IHtmlTag out, IUiRef<T> instanceRef, FieldDeclGroup group,
            IOptions options)
            throws ViewBuilderException, IOException {
        return false;
    }

    @Override
    protected IHtmlTag beginCategory(IHtmlViewContext ctx, IHtmlTag out, FieldCategory category)
            throws ViewBuilderException, IOException {
        String catName = category == FieldCategory.NULL ? "null" : category.getName();
        out = out.fieldset().class_("zu-fcat").id("zp-fcat-" + catName);

        // IHtmlTag head = out.h2().id("zp-fcat-" + catName);
        IHtmlTag head = out.legend();

        head.span().class_("icon fa").text(FA_CUBE);
        if (category == FieldCategory.NULL) {
            head.text("基本信息");
        } else {
            head.text(IXjdocElement.fn.labelName(category));
        }
        return out;
    }

    @Override
    protected void endCategory(IHtmlViewContext ctx, IHtmlTag out, IHtmlTag catOut, FieldCategory category) {
    }

    @Override
    protected List<IFieldDecl> processFieldSelection(IHtmlViewContext ctx, IHtmlTag out, IUiRef<T> instanceRef,
            FieldDeclGroup group, List<IFieldDecl> selection, IOptions options)
            throws ViewBuilderException, IOException {
        if (group.getCategory() == FieldCategory.NULL) {
            Set<String> includes = new HashSet<String>();
            Set<String> excludes = new HashSet<String>();
            selectBasicFields(includes, excludes);
            if (includes.isEmpty())
                includes = null;
            if (excludes.isEmpty())
                excludes = null;

            Iterator<IFieldDecl> iterator = selection.iterator();
            while (iterator.hasNext()) {
                IFieldDecl field = iterator.next();
                boolean included = includes == null || includes.contains(field.getName());
                boolean excluded = excludes != null && excludes.contains(field.getName());
                if (!included || excluded)
                    iterator.remove();
            }
        }

        Collections.sort(selection, FieldDeclLabelComparator.INSTANCE);
        return selection;
    }

    protected void selectBasicFields(Set<String> includes, Set<String> excludes)
            throws ViewBuilderException, IOException {
    }

    @Override
    protected IHtmlTag beginField(IHtmlViewContext ctx, IHtmlTag out, IFieldDecl fieldDecl)
            throws ViewBuilderException, IOException {
        HtmlDivTag div = out.div().class_("zu-field");
        div.attr("f", fieldDecl.getName());

        IHtmlTag labelDiv = div.label().class_("zu-flabel");
        String labelName = IXjdocElement.fn.labelName(fieldDecl);
        labelDiv.text(labelName + ":");

        // IHtmlTag valueDiv = labelDiv.div().class_("zu-fvalue");
        IHtmlTag valueDiv = div.span().class_("zu-fvalue");
        return valueDiv;
    }

    @Override
    protected void fieldBody(IHtmlViewContext ctx, IHtmlTag out, IUiRef<T> instanceRef, IFieldDecl fieldDecl,
            IOptions options)
            throws ViewBuilderException, IOException {
        IProperty property = fieldDecl.getProperty();
        UiPropertyRef<Object> propertyRef = new UiPropertyRef<Object>(instanceRef, property);
        Object value = propertyRef.get();

        Class<?> type = property.getPropertyType();
        if (CoEntity.class.isAssignableFrom(type)) {
            CoEntity entity = (CoEntity) value;

            String inputName = fieldDecl.getInputName();
            if (inputName == null)
                inputName = fieldDecl.getName();

            HtmlInputTag input = out.input().type("hidden").name(inputName);
            FieldHtmlUtil.apply(input, fieldDecl, options);

            HtmlSpanTag span = out.span();
            span.attr("ec", type.getSimpleName());

            if (entity != null) {
                input.value(entity.getId().toString());
                span.attr("eid", entity.getId());
                span.text(entity.getLabel());
            } else {
                span.text(null);
            }

            out.a().href("javascript: alert(1)").text("选择");
            return;
        }

        IHtmlViewBuilder<Object> viewBuilder;

        HtmlViewBuilder aViewBuilder = property.getAnnotation(HtmlViewBuilder.class);
        if (aViewBuilder != null && aViewBuilder.value().length > 0) {
            viewBuilder = (IHtmlViewBuilder<Object>) SingletonUtil.instantiateCached(aViewBuilder.value()[0]);
        } else {
            IHtmlViewBuilderFactory factory = ctx.query(IHtmlViewBuilderFactory.class);
            if (factory == null)
                throw new IllegalConfigException(IHtmlViewBuilderFactory.class + " isn't set.");
            viewBuilder = factory.getViewBuilder(type);
        }

        out.comment("Foo-Field: " + type.getSimpleName() + " -- " + viewBuilder.getClass().getSimpleName());
        viewBuilder.buildView(ctx, out, propertyRef, options);
    }

    @Override
    protected void endField(IHtmlViewContext ctx, IHtmlTag out, IHtmlTag fieldOut, IFieldDecl fieldDecl)
            throws ViewBuilderException, IOException {
    }

}
