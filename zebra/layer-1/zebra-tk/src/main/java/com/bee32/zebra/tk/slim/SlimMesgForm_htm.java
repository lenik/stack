package com.bee32.zebra.tk.slim;

import java.io.IOException;
import java.util.Set;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlInputTag;
import net.bodz.bas.html.dom.tag.HtmlLabelTag;
import net.bodz.bas.html.dom.tag.HtmlOptionTag;
import net.bodz.bas.html.dom.tag.HtmlSelectTag;
import net.bodz.bas.html.dom.tag.HtmlTextareaTag;
import net.bodz.bas.html.util.FieldHtmlUtil;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.form.FieldCategory;
import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.form.IFieldDecl;
import net.bodz.bas.repr.form.IFormDecl;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.SchemaPref;
import net.bodz.lily.model.base.schema.FormDef;
import net.bodz.lily.model.base.schema.impl.FormDefCriteria;
import net.bodz.lily.model.base.schema.impl.FormDefMapper;
import net.bodz.lily.model.mx.base.CoMessage;

import com.bee32.zebra.tk.hbin.SplitForm;

public abstract class SlimMesgForm_htm<T extends CoMessage<?>>
        extends SlimForm_htm<T> {

    public SlimMesgForm_htm(Class<?> valueClass, String... supportedFeatures) {
        super(valueClass, supportedFeatures);
    }

    @Override
    protected boolean buildBasicGroup(IHttpViewContext ctx, IHtmlTag out, IUiRef<?> instanceRef, FieldDeclGroup group,
            IOptions options)
            throws ViewBuilderException {
        IFormDecl formDecl = group.getFormDecl();
        CoMessage<?> instance = (CoMessage<?>) instanceRef.get();

        SplitForm form = (SplitForm) out.getParent();
        out = form.head.table().class_("zu-msg");

        SchemaPref aSchemaPref = getValueType().getAnnotation(SchemaPref.class);
        if (aSchemaPref != null) {
            int schemaId = aSchemaPref.value();
            int formId = instance.getForm() == null ? aSchemaPref.form() : instance.getForm().getId();

            IHtmlTag formLine = out.tr().class_("msg-form");
            IFieldDecl formfd = formDecl.getFieldDecl("form");
            IHtmlTag formLabel = formLine.th().label();
            formLabel.span().class_("fa icon").text(FA_BARS);
            formLabel.text(formfd.getLabel() + "：");
            HtmlSelectTag formInput = formLine.td().select().id("form");

            FormDefCriteria criteria = FormDefCriteria.forSchema(schemaId);
            for (FormDef formDef : ctx.query(FormDefMapper.class).filter(criteria)) {
                HtmlOptionTag option = formInput.option().value(formDef.getId()).text(formDef.getLabel());
                if (formId == formDef.getId())
                    option.selected("selected");
            }
            FieldHtmlUtil.apply(formInput, formfd, options);
        }

        IHtmlTag subjectLine = out.tr().class_("msg-subject");
        {
            IFieldDecl subjectDecl = formDecl.getFieldDecl("subject");
            IHtmlTag subjectLabel = subjectLine.th().label();
            subjectLabel.span().class_("fa icon").text(FA_CHEVRON_DOWN);
            subjectLabel.text(subjectDecl.getLabel() + "：");

            HtmlInputTag subjectInput = subjectLine.td().input().name("subject").type("text");
            subjectInput.value(instance.getSubject());
            FieldHtmlUtil.apply(subjectInput, subjectDecl, options);
        }

        IHtmlTag textLine = out.tr().class_("msg-text");
        {
            IFieldDecl textDecl = formDecl.getFieldDecl("text");
            HtmlLabelTag textLabel = textLine.th().label();
            textLabel.span().class_("fa icon").text(FA_FILE_O);
            textLabel.text(textDecl.getLabel() + "：");

            HtmlTextareaTag textarea = textLine.td().textarea().name("text");
            String text = instance.getText();
            textarea.text(text == null ? "" : text);
            FieldHtmlUtil.apply(textarea, textDecl, options);
        }

        form.sep.hr();
        return false;
    }

    @Override
    protected void selectFields(FieldDeclGroup group, Set<String> includes, Set<String> excludes)
            throws ViewBuilderException, IOException {
        if (group.getCategory() == FieldCategory.NULL) {
            excludes.add("subject");
            excludes.add("text");
            excludes.add("textPreview");
            excludes.add("image");

            excludes.add("codeName");
            excludes.add("label");
            excludes.add("description");
        }
    }

}
