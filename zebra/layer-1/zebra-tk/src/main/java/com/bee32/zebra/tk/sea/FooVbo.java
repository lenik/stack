package com.bee32.zebra.tk.sea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.bodz.bas.c.type.TypeId;
import net.bodz.bas.c.type.TypeKind;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlH2Tag;
import net.bodz.bas.html.dom.tag.HtmlInputTag;
import net.bodz.bas.html.dom.tag.HtmlSelectTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.i18n.dom.iString;
import net.bodz.bas.meta.bean.DetailLevel;
import net.bodz.bas.potato.element.IProperty;
import net.bodz.bas.potato.element.IPropertyAccessor;
import net.bodz.bas.repr.form.FieldCategory;
import net.bodz.bas.repr.form.IFieldDef;
import net.bodz.bas.repr.form.IFormDef;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.mda.xjdoc.model.javadoc.IXjdocElement;

import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CE;
import com.tinylily.model.base.CoEntity;

public abstract class FooVbo<T extends CoEntity>
        extends Zc3Template_CE<T>
        implements IZebraSiteAnchors, IFontAwesomeCharAliases {

    public FooVbo(Class<?> valueClass, String... supportedFeatures) {
        super(valueClass, supportedFeatures);
    }

    @Override
    public final IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<T> ref, IOptions options)
            throws ViewBuilderException, IOException {
        ctx = super.buildHtmlView(ctx, ref, options);

        PageStruct p = new PageStruct(ctx);
        IHtmlTag out = p.mainCol;

        T o = ref.get();
        IFormDef formStruct = o.getFormDef();

        Map<FieldCategory, Collection<IFieldDef>> map = FieldCategory.group(//
                formStruct.getFieldDefs(DetailLevel.EXTEND));

        for (FieldCategory category : map.keySet()) {
            List<IFieldDef> fieldDefs = new ArrayList<>();
            for (IFieldDef fieldDef : map.get(category))
                if (filterField(fieldDef))
                    fieldDefs.add(fieldDef);
            if (fieldDefs.isEmpty())
                continue;

            buildCategoryHeader(ctx, out, category);
            buildFields(ctx, out, fieldDefs, o);

            out.hr();
        } // for field-group
        return ctx;
    }

    protected boolean filterField(IFieldDef formField) {
        return true;
    }

    protected void buildCategoryHeader(IHtmlViewContext ctx, IHtmlTag out, FieldCategory category)
            throws ViewBuilderException {
        HtmlH2Tag fgTitle = out.h2();
        fgTitle.span().class_("icon fa").text(FA_CUBE);
        if (category == FieldCategory.NULL)
            fgTitle.text("基本信息");
        else
            fgTitle.text(IXjdocElement.fn.labelName(category));
    }

    protected void buildFields(IHtmlViewContext ctx, IHtmlTag out, List<IFieldDef> fieldDefs, T o)
            throws ViewBuilderException {
        out = out.div().class_("container");
        for (IFieldDef fieldDef : fieldDefs) {
            IPropertyAccessor accessor = fieldDef.getAccessor();
            Object value;
            try {
                value = accessor.getValue(o);
                if (value == null)
                    switch (fieldDef.getNullConvertion()) {
                    case EMPTY:
                        value = "";
                    default:
                    }
            } catch (ReflectiveOperationException e) {
                value = "(" + e.getClass().getName() + ") " + e.getMessage();
            }

            HtmlDivTag row = out.div().class_("container-row");
            HtmlDivTag labelDiv = row.div().class_("col-sm-3 col-lg-2");
            labelDiv.text(IXjdocElement.fn.labelName(fieldDef) + ":");
            HtmlDivTag valueDiv = row.div().class_("col-sm-9 col-lg-10");

            buildField(ctx, valueDiv, fieldDef, value);

        } // for field
    }

    protected void buildField(IHtmlViewContext ctx, IHtmlTag out, IFieldDef fieldDef, Object value)
            throws ViewBuilderException {
        // IViewBuilderFactory factory = ctx.query(IViewBuilderFactory.class);

        Class<?> type = fieldDef.getValueType();
        IProperty property = fieldDef.getProperty();
        // IElementDoc xjdoc = property.getXjdoc();

        iString placeholder = fieldDef.getPlaceholder();
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

    }

}
