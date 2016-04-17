package com.bee32.zebra.tk.hbin;

import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlDiv;
import net.bodz.bas.html.io.tag.HtmlInput;
import net.bodz.bas.html.io.tag.HtmlSpan;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;

import com.bee32.zebra.tk.site.IZebraSiteAnchors;

public class UploadFileDialog_htm
        extends SimpleDialog
        implements IZebraSiteAnchors, IFontAwesomeCharAliases {

    String uploadHandlerUrl = _webApp_ + "upload";

    private HtmlDiv buttons;
    private HtmlInput fileInput;
    private HtmlDiv alerts;

    public String dataForform;
    public String dataBind;

    public boolean acceptCamera;
    public String ondone;

    @Override
    public IHtmlOut build(IHtmlOut out, String id, String... styleClasses) {
        if (dataForform != null)
            out.attr("data-forform", dataForform);
        if (dataBind != null)
            out.attr("data-bind", dataBind);

        out = super.build(out, id, "fileupload-dialog");

        buttons = out.div();

        HtmlSpan addButton = buttons.span().class_("btn btn-link fileinput-button")//
                .iText(FA_PLUS_CIRCLE, "fa").text("发送文件");

        fileInput = addButton.input().class_("fileupload").type("file").name("files[]");
        fileInput.attr("data-url", uploadHandlerUrl);

        if (acceptCamera)
            fileInput.acceptCamera("1");
        if (ondone != null)
            fileInput.attr("ondone", ondone);

        HtmlDiv progress = out.div().class_("fileupload-progress progress").style("display: none");
        HtmlDiv bar = progress.div()//
                .class_("progress-bar progress-bar-success progress-striped active") //
                .attr("role", "progressbar")//
                .attr("aria-valuemin", 0)//
                .attr("aria-valuemax", 100)//
                .style("width: 0%");
        bar.text("");

        alerts = out.div().id("alerts");
        HtmlDiv success = alerts.div().class_("alert alert-success").style("display: none");
        success.a().class_("close").attr("data-dismiss", "alert").verbatim("&times;");
        success.iText(FA_CHECK_CIRCLE, "fa");
        success.bText("[成功]").text("上传成功");
        success.hr();
        success.div().class_("message small").text("Succeeded.");
        success.div().class_("alert-warning small").iText(FA_EXCLAMATION_TRIANGLE, "fa")
                .text("在您提交这个更改以前，上传的文件并不会自动保存。");

        return out;
    }

}
