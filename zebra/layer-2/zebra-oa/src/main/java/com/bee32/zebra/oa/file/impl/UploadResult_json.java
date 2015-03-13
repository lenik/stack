package com.bee32.zebra.oa.file.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.json.JSONArray;
import org.json.JSONObject;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.viz.AbstractHttpViewBuilder;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.std.rfc.mime.ContentType;
import net.bodz.bas.std.rfc.mime.ContentTypes;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.file.FileManager;
import com.bee32.zebra.tk.site.IZebraSiteAnchors;

public class UploadResult_json
        extends AbstractHttpViewBuilder<UploadResult>
        implements IZebraSiteAnchors {

    public UploadResult_json() {
        super(UploadResult.class);
    }

    @Override
    public ContentType getContentType(HttpServletRequest request, UploadResult value) {
        return ContentTypes.application_json;
    }

    @Override
    public IHtmlTag buildHtmlView(IHttpViewContext ctx, IHtmlTag parent, IUiRef<UploadResult> ref, IOptions options)
            throws ViewBuilderException, IOException {
        UploadResult result = ref.get();
        FileManager manager = FileManager.forCurrentRequest();

        JSONArray filesArray = new JSONArray();
        for (FileItem item : result.items) {
            JSONObject fileObj = new JSONObject();
            fileObj.put("name", item.getName());
            fileObj.put("size", item.getSize());
            fileObj.put("url", _webApp_ + manager.incomingPrefix + item.getName());
            fileObj.put("thumbnail_url", manager.incomingPrefix + item.getName() + "?resize=100");
            fileObj.put("delete_url", manager.incomingPrefix + item.getName());
            fileObj.put("delete_type", "DELETE");
            filesArray.put(fileObj);
        }

        JSONObject obj = new JSONObject();
        obj.put("files", filesArray);

        PrintWriter out = ctx.getResponse().getWriter();
        String json = obj.toString();
        out.println(json);
        out.close();
        return null;
    }

}