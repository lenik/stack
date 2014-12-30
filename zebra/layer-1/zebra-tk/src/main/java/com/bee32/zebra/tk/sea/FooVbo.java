package com.bee32.zebra.tk.sea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlH2Tag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
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

        Map<FieldCategory, Collection<IFieldDef>> fgMap = FieldCategory.group(//
                formStruct.getFieldDefs(DetailLevel.EXTEND));

        for (FieldCategory category : fgMap.keySet()) {
            List<IFieldDef> fieldDefs = new ArrayList<>();
            for (IFieldDef fieldDef : fgMap.get(category))
                if (filterField(fieldDef))
                    fieldDefs.add(fieldDef);
            if (fieldDefs.isEmpty())
                continue;

            buildFieldGroupTitle(ctx, out, category);
            buildFormFields(ctx, out, fieldDefs, o);

            out.hr();
        } // for field-group
        return ctx;
    }

    protected boolean filterField(IFieldDef formField) {
        return true;
    }

    protected void buildFieldGroupTitle(IHtmlViewContext ctx, IHtmlTag out, FieldCategory fieldGroup) {
        HtmlH2Tag fgTitle = out.h2();
        fgTitle.span().class_("icon fa").text(FA_CUBE);
        if (fieldGroup == FieldCategory.NULL)
            fgTitle.text("基本信息");
        else
            fgTitle.text(IXjdocElement.fn.labelName(fieldGroup));
    }

    protected void buildFormFields(IHtmlViewContext ctx, IHtmlTag out, List<IFieldDef> fields, T o) {
        HtmlDivTag fgDiv = out.div().class_("container");
        for (IFieldDef field : fields) {
            IPropertyAccessor accessor = field.getAccessor();
            Object value;
            try {
                value = accessor.getValue(o);
                if (value == null)
                    switch (field.getNullConvertion()) {
                    case EMPTY:
                        value = "";
                    default:
                    }
            } catch (ReflectiveOperationException e) {
                value = "(" + e.getClass().getName() + ") " + e.getMessage();
            }

            HtmlDivTag row = fgDiv.div().class_("container-row");
            HtmlDivTag labelDiv = row.div().class_("col-sm-3 col-lg-2");
            labelDiv.text(IXjdocElement.fn.labelName(field) + ":");
            HtmlDivTag valueDiv = row.div().class_("col-sm-9 col-lg-10");

            IProperty property = null;
            if (accessor instanceof IProperty)
                property = (IProperty) accessor;
            buildFieldValue(ctx, valueDiv, value, property);
// IRefEntry<T>
// new UiPropertyRef<Object>(entry, property);
        } // for field
    }

    protected void buildFieldValue(IHtmlViewContext ctx, IHtmlTag out, Object value, IProperty property) {
        // IViewBuilderFactory viewBuilderFactory = ctx.query(IViewBuilderFactory.class);
        // viewBuilderFactory.buildView(ctx, ref);

    }

}
