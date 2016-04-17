package com.bee32.zebra.tk.hbin;

import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlDiv;
import net.bodz.bas.html.io.tag.HtmlInput;
import net.bodz.bas.html.io.tag.HtmlTable;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;

import com.bee32.zebra.tk.site.IZebraSiteAnchors;

/**
 * @template <pre>
 * <script id="template-upload" type="text/x-tmpl">
 *   {% for (var i=0, file; file=o.files[i]; i++) { %}
 *     <tr class="template-upload fade">
 *         <td><i class="preview"></i>
 *         </td>
 *         <td><p class="name">{%=file.name%}</p>
 *             <strong class="error"/>
 *         </td>
 *         <td><p class="size">Processing...</p>
 *             <div class="progress"/>
 *         </td>
 *         <td>
 *           {% if (!i &amp;&amp; !o.options.autoUpload) { %}
 *             <button class="start" disabled="disabled">Start</button>
 *           {% } %}
 *           {% if (!i) { %}
 *             <button class="cancel">Cancel</button>
 *           {% } %}
 *         </td>
 *     </tr>
 *   {% } %}
 * </script>
 * </pre>
 */
public class UploadFilesDialog
        extends SimpleDialog
        implements IZebraSiteAnchors, IFontAwesomeCharAliases {

    @Override
    public IHtmlOut build(IHtmlOut out, String id, String... styleClasses) {
        out = super.build(out, id, styleClasses);

        out = out.div().class_("zu-upload");
        HtmlDiv btns = out.div().class_("fileupload-buttonbar");

        HtmlInput fileupload = btns.span().class_("btn btn-success fileinput-button")//
                .iText(FA_PLUS_CIRCLE, "fa").text("上传...")//
                .input().id("fileupload").type("file").name("files[]");
        fileupload.attr("data-url", _webApp_ + "file/upload");

        btns.button().type("submit").class_("btn btn-primary start")//
                .iText(FA_UPLOAD, "fa").text("开始上传");

        btns.button().type("reset").class_("btn btn-warning cancel")//
                .iText(FA_TIMES, "fa").text("取消上传");

        btns.button().type("button").class_("btn btn-danger delete")//
                .iText(FA_TRASH, "fa").text("删除");

        HtmlTable presentation = out.table().attr("role", "presentation");
        presentation.tbody().class_("files");

        out.div().class_("log");

        return out;
    }

}
