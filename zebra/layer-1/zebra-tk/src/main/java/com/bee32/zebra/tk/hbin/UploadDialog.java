package com.bee32.zebra.tk.hbin;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlInputTag;
import net.bodz.bas.html.dom.tag.HtmlScriptTag;
import net.bodz.bas.html.dom.tag.HtmlTableTag;
import net.bodz.bas.html.dom.tag.HtmlTdTag;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;

import com.bee32.zebra.tk.site.IZebraSiteAnchors;

public class UploadDialog
        extends SimpleDialog
        implements IZebraSiteAnchors, IFontAwesomeCharAliases {

    public UploadDialog(IHtmlTag parent, String id) {
        super(parent, id);

        HtmlScriptTag script = parent.script().id("template-upload").type("text/x-tmpl");
        script.text("{% for (var i=0, file; file=o.files[i]; i++) { %}");
        HtmlTrTag tr = script.tr().class_("template-upload fade");
        tr.td().iText("", "preview");
        HtmlTdTag td = tr.td();
        td.p().class_("name").text("{%=file.name%}");
        td.strong().class_("error");
        td = tr.td();
        td.p().class_("size").text("Processing...");
        td.div().class_("progress");
        td = tr.td();
        td.text("{% if (!i && !o.options.autoUpload) { %}");
        td.button().class_("start").disabled("disabled").text("Start");
        td.text("{% } %}");
        td.text("{% if (!i) { %}");
        td.button().class_("cancel").text("Cancel");
        td.text("{% } %}");
        script.text("{% } %}");
    }

    @Override
    protected void create() {
        HtmlDivTag out = div().class_("zu-upload");
        HtmlDivTag btns = out.div().class_("fileupload-buttonbar");

        HtmlInputTag fileupload = btns.span().class_("btn btn-success fileinput-button")//
                .iText(FA_PLUS_CIRCLE, "fa").text("上传...")//
                .input().id("fileupload").type("file").name("files[]");
        fileupload.attr("data-url", _webApp_ + "file/upload");

        btns.button().type("submit").class_("btn btn-primary start")//
                .iText(FA_UPLOAD, "fa").text("开始上传");

        btns.button().type("reset").class_("btn btn-warning cancel")//
                .iText(FA_TIMES, "fa").text("取消上传");

        btns.button().type("button").class_("btn btn-danger delete")//
                .iText(FA_TRASH, "fa").text("删除");

        HtmlTableTag presentation = out.table().attr("role", "presentation");
        presentation.tbody().class_("files");

        out.div().class_("log");
    }

}
