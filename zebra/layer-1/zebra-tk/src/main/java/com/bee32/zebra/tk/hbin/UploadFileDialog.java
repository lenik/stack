package com.bee32.zebra.tk.hbin;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlInputTag;
import net.bodz.bas.html.dom.tag.HtmlSpanTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;

import com.bee32.zebra.tk.site.IZebraSiteAnchors;

public class UploadFileDialog
        extends SimpleDialog
        implements IZebraSiteAnchors, IFontAwesomeCharAliases {

    String uploadHandlerUrl = _webApp_ + "upload";

    public HtmlDivTag buttons;
    public HtmlInputTag fileInput;
    public HtmlDivTag alerts;

    public UploadFileDialog(IHtmlTag parent, String id) {
        super(parent, id);
        class_("dialog fileupload-dialog");
    }

    @Override
    public void build() {
        buttons = div();

        HtmlSpanTag addButton = buttons.span().class_("btn btn-link fileinput-button")//
                .iText(FA_PLUS_CIRCLE, "fa").text("发送文件");

        fileInput = addButton.input().class_("fileupload").type("file").name("files[]");
        fileInput.attr("data-url", uploadHandlerUrl);

        HtmlDivTag progress = div().class_("fileupload-progress progress").style("display: none");
        HtmlDivTag bar = progress.div()//
                .class_("progress-bar progress-bar-success progress-striped active") //
                .attr("role", "progressbar")//
                .attr("aria-valuemin", 0)//
                .attr("aria-valuemax", 100)//
                .style("width: 0%");
        bar.text("");

        alerts = div().id("alerts");
        HtmlDivTag success = alerts.div().class_("alert alert-success").style("display: none");
        success.a().class_("close").attr("data-dismiss", "alert").verbatim("&times;");
        success.iText(FA_CHECK_CIRCLE, "fa");
        success.bText("[成功]").text("上传成功");
        success.hr();
        success.div().class_("message small").text("Succeeded.");
        success.div().class_("alert-warning small").iText(FA_EXCLAMATION_TRIANGLE, "fa")
                .text("在您提交这个更改以前，上传的文件并不会自动保存。");
    }

}
