package com.bee32.zebra.oa.contact.impl;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlA;
import net.bodz.bas.html.io.tag.HtmlDiv;
import net.bodz.bas.html.io.tag.HtmlImg;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.potato.PotatoTypes;
import net.bodz.bas.potato.element.IType;
import net.bodz.bas.repr.form.FormDeclBuilder;
import net.bodz.bas.repr.form.MutableFormDecl;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.contact.Contact;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.oa.file.ZebraFilePathMapping;
import com.bee32.zebra.tk.hbin.ItemsTable;
import com.bee32.zebra.tk.hbin.SectionDiv_htm1;
import com.bee32.zebra.tk.hbin.UploadFileDialog_htm;
import com.bee32.zebra.tk.slim.SlimPathFieldMap;
import com.bee32.zebra.tk.slim.SlimForm_htm;

public class Person_htm
        extends SlimForm_htm<Person> {

    public Person_htm() {
        super(Person.class);
    }

    @Override
    protected IHtmlOut beforeForm(IHtmlViewContext ctx, IHtmlOut out, IUiRef<Person> ref)
            throws ViewBuilderException, IOException {
        out = super.beforeForm(ctx, out, ref);

        Integer id = ref.get().getId();
        if (id == null)
            return out;

        return out;
    }

    @Override
    protected IHtmlOut afterForm(IHtmlViewContext ctx, IHtmlOut out, IUiRef<Person> ref)
            throws ViewBuilderException, IOException {
        Person person = ref.get();
        Integer id = person.getId();
        IHtmlOut sect;
        if (id != null) {
            sect = new SectionDiv_htm1("形象", IFontAwesomeCharAliases.FA_PICTURE_O).build(out, "s-avatar");
            buildAvatar(ctx, sect, person);

            sect = new SectionDiv_htm1("联系方式", IFontAwesomeCharAliases.FA_PHONE_SQUARE).build(out, "s-contact");
            buildContacts(ctx, sect, person);
        }
        return out;
    }

    void buildAvatar(IHtmlViewContext ctx, IHtmlOut out, Person person)
            throws ViewBuilderException, IOException {
        HttpServletRequest req = ctx.getRequest();

        HtmlA uploadLink = out.a().href("javascript: uploadDialog.open()");
        uploadLink.iText(FA_CAMERA, "fa").text("上传...");

        int id = person.getId();
        ZebraFilePathMapping filePathMapping = ZebraFilePathMapping.getInstance();
        String photoPath = id + ".jpg";
        File photoFile = filePathMapping.getLocalFile(req, "avatar", photoPath);

        HtmlDiv div = out.div().id("avatar-div");
        HtmlImg img = div.img().id("avatar").height("128");
        if (photoFile.exists())
            img.src(_webApp_ + "files/avatar/" + photoPath);
        else
            div.style("display: none");

        div.a().href("javascript: saveAvatar()").iText(FA_FLOPPY_O, "fa").text("保存形象");

        UploadFileDialog_htm dialog = new UploadFileDialog_htm();
        dialog.acceptCamera = true;
        dialog.dataBind = "#uploaded-file";
        dialog.ondone = "previewAvatar(files)";
        dialog.build(out, "uploadDialog");
    }

    void buildContacts(IHtmlViewContext ctx, IHtmlOut out, Person person)
            throws ViewBuilderException, IOException {
        IType itemType = PotatoTypes.getInstance().forClass(Contact.class);
        FormDeclBuilder formDeclBuilder = new FormDeclBuilder();
        MutableFormDecl itemFormDecl;
        try {
            itemFormDecl = formDeclBuilder.build(itemType);
        } catch (ParseException e) {
            throw new ViewBuilderException(e.getMessage(), e);
        }

        SlimPathFieldMap fields = new SlimPathFieldMap(itemFormDecl);
        try {
            fields.parse("i*", ContactIndex_htm.FIELDS);
        } catch (Exception e) {
            throw new ViewBuilderException(e.getMessage(), e);
        }

        Integer id = person.getId();

        ItemsTable itab = new ItemsTable(ctx, fields.values());
        itab.editorUrl = _webApp_ + "contact/ID/?view:=form&person.id=" + id;
        itab.ajaxUrl = "../../contact/data.json?person=" + id;
        HtmlTbody tbody = itab.buildViewStart(out, "contacts");
        itab.buildViewEnd(tbody);
    }

}
