package com.bee32.zebra.tk.sea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.bodz.bas.c.type.TypeId;
import net.bodz.bas.c.type.TypeKind;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlH2Tag;
import net.bodz.bas.html.dom.tag.HtmlInputTag;
import net.bodz.bas.html.dom.tag.HtmlSelectTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.AbstractHtmlViewBuilder;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.i18n.dom.iString;
import net.bodz.bas.meta.bean.DetailLevel;
import net.bodz.bas.potato.element.IProperty;
import net.bodz.bas.potato.element.IPropertyAccessor;
import net.bodz.bas.repr.form.FieldCategory;
import net.bodz.bas.repr.form.FieldDeclFilters;
import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.form.IFieldDecl;
import net.bodz.bas.repr.form.IFormDecl;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.mda.xjdoc.model.javadoc.IXjdocElement;

import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.site.PageStruct;
import com.tinylily.model.base.CoObject;

public abstract class FooVbo2<T extends CoObject>
        extends AbstractHtmlViewBuilder<T>
        implements IZebraSiteAnchors, IFontAwesomeCharAliases {

    public FooVbo2(Class<?> valueClass, String... supportedFeatures) {
        super(valueClass, supportedFeatures);
    }

    @Override
    public IHtmlTag buildHtmlView(IHtmlViewContext ctx, IHtmlTag out, IUiRef<T> ref, IOptions options)
            throws ViewBuilderException, IOException {

        PageStruct p = new PageStruct(ctx);
        out = p.mainCol;

        T o = ref.get();
        if (o == null) {
            out.text("null@" + ref.getValueType().getSimpleName());
            return out;
        }

        IFormDecl formDecl = IFormDecl.fn.forClass(ref.getValueType());

        Collection<FieldDeclGroup> groups = formDecl
                .getFieldGroups(FieldDeclFilters.maxDetailLevel(DetailLevel.DETAIL));

        for (FieldDeclGroup group : groups) {
            FieldCategory category = group.getCategory();

            List<IFieldDecl> fieldDecls = new ArrayList<>();
            for (IFieldDecl fieldDecl : group)
                if (filterField(fieldDecl))
                    fieldDecls.add(fieldDecl);
            if (fieldDecls.isEmpty())
                continue;

            buildCategoryHeader(ctx, out, category);
            buildFields(ctx, out, ref, fieldDecls);

            out.hr();
        } // for field-group
        return out;
    }

    protected boolean filterField(IFieldDecl formField) {
        IProperty property = formField.getProperty();
        if (property == null)
            return false;

        if (!property.isReadable())
            return false;

        return true;
    }

    protected void buildCategoryHeader(IHtmlViewContext ctx, IHtmlTag out, FieldCategory category)
            throws ViewBuilderException {
        HtmlH2Tag h2 = out.h2();
        h2.span().class_("icon fa").text(FA_CUBE);
        if (category == FieldCategory.NULL)
            h2.text("基本信息");
        else
            h2.text(IXjdocElement.fn.labelName(category));
    }

    protected void buildFields(IHtmlViewContext ctx, IHtmlTag out, IUiRef<T> ref, List<IFieldDecl> fieldDecls)
            throws ViewBuilderException {
        out = out.div().class_("container");
        for (IFieldDecl fieldDecl : fieldDecls) {
            HtmlDivTag row = out.div().class_("container-row");

            HtmlDivTag labelDiv = row.div().class_("col-sm-3 col-lg-2");
            String labelName = IXjdocElement.fn.labelName(fieldDecl);
            labelDiv.text(labelName + ":");

            HtmlDivTag valueDiv = row.div().class_("col-sm-9 col-lg-10");

            buildField(ctx, valueDiv, ref, fieldDecl);
        } // for field
    }

    protected void buildField(IHtmlViewContext ctx, IHtmlTag out, Object instance, IFieldDecl fieldDecl)
            throws ViewBuilderException {
        IPropertyAccessor accessor = fieldDecl.getAccessor();
        Object value;
        try {
            value = accessor.getValue(instance);
            if (value == null)
                switch (fieldDecl.getNullConvertion()) {
                case EMPTY:
                    value = "";
                default:
                }
        } catch (ReflectiveOperationException e) {
            value = "(" + e.getClass().getName() + ") " + e.getMessage();
        }

        Class<?> type = fieldDecl.getValueType();
        // IElementDoc xjdoc = property.getXjdoc();

        iString placeholder = fieldDecl.getPlaceholder();
        HtmlInputTag input = null;
        HtmlSelectTag select = null;

        switch (TypeKind.getTypeId(type)) {
        case TypeId.STRING:
            input = out.input().type("text").text(placeholder);
            break;

        case TypeId._char:
        case TypeId.CHARACTER:
            input = out.input().type("text").text(placeholder);
            break;

        case TypeId._boolean:
        case TypeId.BOOLEAN:
            input = out.input().type("checkbox");
            break;

        case TypeId._byte:
        case TypeId._short:
        case TypeId._int:
        case TypeId._long:
        case TypeId.BYTE:
        case TypeId.SHORT:
        case TypeId.INTEGER:
        case TypeId.LONG:
            input = out.input().type("text").text(placeholder);
            break;

        case TypeId.DATE:
        case TypeId.SQL_DATE:
        case TypeId.CALENDAR:
            input = out.input().type("calendar");
            break;

        case TypeId.ENUM:
            select = out.select();
            break;
        } // switch type-id

        if (value != null)
            if (input != null)
                input.value(value.toString());
    }

}
