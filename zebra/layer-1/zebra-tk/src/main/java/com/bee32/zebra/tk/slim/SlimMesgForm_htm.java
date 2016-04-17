package com.bee32.zebra.tk.slim;

import java.io.IOException;
import java.util.Set;

import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlInput;
import net.bodz.bas.html.io.tag.HtmlLabel;
import net.bodz.bas.html.io.tag.HtmlOption;
import net.bodz.bas.html.io.tag.HtmlSelect;
import net.bodz.bas.html.io.tag.HtmlTextarea;
import net.bodz.bas.html.util.FieldDeclToHtml;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.form.FieldCategory;
import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.form.IFieldDecl;
import net.bodz.bas.repr.form.IFormDecl;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.SchemaPref;
import net.bodz.lily.model.base.schema.FormDef;
import net.bodz.lily.model.base.schema.impl.FormDefMapper;
import net.bodz.lily.model.base.schema.impl.FormDefMask;
import net.bodz.lily.model.mx.base.CoMessage;

public abstract class SlimMesgForm_htm<T extends CoMessage<?>>
        extends SlimForm_htm<T> {

    public SlimMesgForm_htm(Class<?> valueClass) {
        super(valueClass);
    }

    @Override
    protected boolean buildBasicGroup(IHtmlViewContext ctx, IHtmlOut out, IUiRef<?> instanceRef, FieldDeclGroup group)
            throws ViewBuilderException {
        IFormDecl formDecl = group.getFormDecl();
        CoMessage<?> instance = (CoMessage<?>) instanceRef.get();

        // -> buildFormHead()...?
        out = out.table().class_("zu-msg");

        SchemaPref aSchemaPref = getValueType().getAnnotation(SchemaPref.class);
        if (aSchemaPref != null) {
            int schemaId = aSchemaPref.value();
            int formId = instance.getForm() == null ? aSchemaPref.form() : instance.getForm().getDefId(0);

            IHtmlOut formLine = out.tr().class_("msg-form");
            IFieldDecl formfd = formDecl.getFieldDecl("form");
            IHtmlOut formLabel = formLine.th().label();
            formLabel.span().class_("fa icon").text(FA_BARS);
            formLabel.text(formfd.getLabel() + "：");
            HtmlSelect formInput = formLine.td().select().id("form");

            FormDefMask mask = FormDefMask.forSchema(schemaId);
            for (FormDef formDef : ctx.query(FormDefMapper.class).filter(mask)) {
                HtmlOption option = formInput.option().value(formDef.getId()).text(formDef.getLabel());
                if (formId == formDef.getId())
                    option.selected("selected");
            }
            FieldDeclToHtml.apply(formInput, formfd);
        }

        IHtmlOut subjectLine = out.tr().class_("msg-subject");
        {
            IFieldDecl subjectDecl = formDecl.getFieldDecl("subject");
            IHtmlOut subjectLabel = subjectLine.th().label();
            subjectLabel.span().class_("fa icon").text(FA_CHEVRON_DOWN);
            subjectLabel.text(subjectDecl.getLabel() + "：");

            HtmlInput subjectInput = subjectLine.td().input().name("subject").type("text");
            subjectInput.value(instance.getSubject());
            FieldDeclToHtml.apply(subjectInput, subjectDecl);
        }

        IHtmlOut textLine = out.tr().class_("msg-text");
        {
            IFieldDecl textDecl = formDecl.getFieldDecl("text");
            HtmlLabel textLabel = textLine.th().label();
            textLabel.span().class_("fa icon").text(FA_FILE_O);
            textLabel.text(textDecl.getLabel() + "：");

            HtmlTextarea textarea = textLine.td().textarea().name("text");
            String text = instance.getText();
            textarea.text(text == null ? "" : text);
            FieldDeclToHtml.apply(textarea, textDecl);
        }

        out.hr();
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
