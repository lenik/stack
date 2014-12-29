package com.bee32.zebra.tk.sea;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.meta.bean.DetailLevel;
import net.bodz.bas.potato.element.IPropertyAccessor;
import net.bodz.bas.repr.form.FieldGroup;
import net.bodz.bas.repr.form.IFormField;
import net.bodz.bas.repr.form.IFormStruct;
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
        implements IZebraSiteAnchors {

    public FooVbo(Class<?> valueClass, String... supportedFeatures) {
        super(valueClass, supportedFeatures);
    }

    @Override
    public final IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<T> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);
        IHtmlTag parent = p.mainCol;

        T o = ref.get();
        IFormStruct formStruct = o.getFormStruct();

        Map<FieldGroup, Collection<IFormField>> fgMap = formStruct.getFieldsGrouped(DetailLevel.EXTEND);
        HtmlDivTag dtab = parent.div().id("data-" + o.getId());
        for (FieldGroup fg : fgMap.keySet()) {
            Collection<IFormField> fields = fgMap.get(fg);
            if (fields.isEmpty())
                continue;

            String fgLabel = fg == FieldGroup.NULL ? "基本信息" : IXjdocElement.fn.labelName(fg);
            dtab.h3().class_("zu-fgroup").text(fgLabel);

            for (IFormField field : fields) {
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

                HtmlDivTag line = dtab.div();
                line.span().class_("zu-flabel").text(IXjdocElement.fn.labelName(field));
                line.span().text(value);
            } // for field

            dtab.hr();
        } // for field-group
        return ctx;
    }

}
