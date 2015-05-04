package com.bee32.zebra.oa.contact.impl;

import java.io.File;
import java.io.IOException;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlATag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlImgTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.potato.PotatoTypes;
import net.bodz.bas.potato.element.IType;
import net.bodz.bas.repr.form.FormDeclBuilder;
import net.bodz.bas.repr.form.MutableFormDecl;
import net.bodz.bas.repr.form.PathFieldMap;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.contact.Contact;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.oa.file.FileManager;
import com.bee32.zebra.tk.hbin.ItemsTable;
import com.bee32.zebra.tk.hbin.SectionDiv;
import com.bee32.zebra.tk.hbin.UploadFileDialog;
import com.bee32.zebra.tk.slim.SlimForm_htm;

public class PersonVbo
        extends SlimForm_htm<Person> {

    public PersonVbo() {
        super(Person.class);
    }

    @Override
    protected IHtmlTag beforeForm(IHttpViewContext ctx, IHtmlTag out, IUiRef<Person> ref, IOptions options)
            throws ViewBuilderException, IOException {
        out = super.beforeForm(ctx, out, ref, options);

        Integer id = ref.get().getId();
        if (id == null)
            return out;

        return out;
    }

    @Override
    protected IHtmlTag afterForm(IHttpViewContext ctx, IHtmlTag out, IUiRef<Person> ref, IOptions options)
            throws ViewBuilderException, IOException {
        Person person = ref.get();
        Integer id = person.getId();
        SectionDiv section;
        if (id != null) {
            section = new SectionDiv(out, "s-avatar", "形象", IFontAwesomeCharAliases.FA_PICTURE_O);
            buildAvatar(ctx, section.contentDiv, person);

            section = new SectionDiv(out, "s-contact", "联系方式", IFontAwesomeCharAliases.FA_PHONE_SQUARE);
            buildContacts(ctx, section.contentDiv, person);
        }
        return out;
    }

    void buildAvatar(IHttpViewContext ctx, IHtmlTag out, Person person)
            throws ViewBuilderException, IOException {
        HtmlATag uploadLink = out.a().href("javascript: uploadDialog.open()");
        uploadLink.iText(FA_CAMERA, "fa").text("上传...");

        int id = person.getId();
        FileManager fileManager = FileManager.forCurrentRequest();
        String photoPath = id + ".jpg";
        File photoFile = fileManager.getFile("avatar", photoPath);

        HtmlDivTag div = out.div().id("avatar-div");
        HtmlImgTag img = div.img().id("avatar").height("128");
        if (photoFile.exists())
            img.src(_webApp_ + "files/avatar/" + photoPath);
        else
            div.style("display: none");

        div.a().href("javascript: saveAvatar()").iText(FA_FLOPPY_O, "fa").text("保存形象");

        UploadFileDialog dialog = new UploadFileDialog(out, "uploadDialog");
        dialog.build();
        dialog.attr("data-bind", "#uploaded-file");
        dialog.fileInput.acceptCamera();
        dialog.fileInput.attr("ondone", "previewAvatar(files)");
    }

    void buildContacts(IHttpViewContext ctx, IHtmlTag out, Person person)
            throws ViewBuilderException, IOException {
        IType itemType = PotatoTypes.getInstance().forClass(Contact.class);
        FormDeclBuilder formDeclBuilder = new FormDeclBuilder();
        MutableFormDecl itemFormDecl;
        try {
            itemFormDecl = formDeclBuilder.build(itemType);
        } catch (ParseException e) {
            throw new ViewBuilderException(e.getMessage(), e);
        }

        PathFieldMap fields = new PathFieldMap(itemFormDecl);
        try {
            fields.parse("i*", ContactIndexVbo.FIELDS);
        } catch (Exception e) {
            throw new ViewBuilderException(e.getMessage(), e);
        }

        Integer id = person.getId();
        ItemsTable itab = new ItemsTable(out, "contacts", //
                _webApp_ + "contact/ID/?view:=form&person.id=" + id);
        itab.ajaxUrl = "../../contact/data.json?person=" + id;
        itab.buildHeader(fields.values());
    }

}
