package com.bee32.sem.file.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;

import javax.free.InputStreamSource;
import javax.free.OutputStreamTarget;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.file.util.HTTPDates;

public class HttpBlobDumper {

    public static final String ATTR_BLOB = "BLOB";
    public static final String ATTR_FILENAME = "FILENAME";
    public static final String ATTR_DESCRIPTION = "DESCRIPTION";

    /**
     * Special action name:
     * <ul>
     * <li>view: View inline.
     * <li>download: Download as attachment.
     * </ul>
     * <p>
     * Parameters:
     * <ul>
     * <li>hash: File blob hash.
     * <li>filename: Download as filename.
     * </ul>
     */
    public static ActionResult dumpBlob(ActionRequest req, ActionResult result)
            throws IOException {

        HttpServletResponse response = result.getResponse();

        FileBlob blob = (FileBlob) req.getAttribute(ATTR_BLOB);
        if (blob == null)
            throw new NullPointerException("blob");

        String filename = (String) req.getAttribute(ATTR_FILENAME);
        String description = (String) req.getAttribute(ATTR_DESCRIPTION);
        String hash = blob.getDigest();

        String contentType = blob.getContentType();
        if (contentType != null)
            response.setContentType(contentType);

        long length = blob.getLength();
        if (length < Integer.MAX_VALUE)
            response.setContentLength((int) length);

        /**
         * @see RFC 2183 2.10
         */
        String actionName = req.getActionName();
        {
            String contentDisposition = null;
            if ("download".equals(actionName)) {
                contentDisposition = "attachment";
                if (filename != null) {
                    /**
                     * @see RFC 5987 2.3
                     */
                    String encodedFilename = URLEncoder.encode(filename, "utf-8");

                    // This is for IE8:
                    // contentDisposition += "; filename=" + encodedFilename;

                    // This is for Firefox and rest.
                    contentDisposition += "; filename*=UTF-8''" + encodedFilename;
                }

            }
            if ("view".equals(actionName))
                contentDisposition = "inline";

            if (contentDisposition != null) {
                response.setHeader("Content-Disposition", contentDisposition);

                if (description != null)
                    response.setHeader("Content-Description", description);
            }
        }

        /**
         * @see RFC 2616 13.3.2
         *      <p>
         *      The ETag response-header field value, an entity tag, provides for an "opaque" cache
         *      validator. This might allow more reliable validation in situations where it is
         *      inconvenient to store modification dates, where the one-second resolution of HTTP
         *      date values is not sufficient, or where the origin server wishes to avoid certain
         *      paradoxes that might arise from the use of modification dates.
         */
        response.setHeader("ETag", "FileBlob/" + hash);

        /**
         * @see RFC 2616 14.9: max-age=delta-seconds
         */
        response.setHeader("Cache-Control", "max-age=" + 365 * 86400);

        /**
         * @see RFC 2616 14.21
         *      <p>
         *      To mark a response as "never expires," an origin server sends an Expires date
         *      approximately one year from the time the response is sent. HTTP/1.1 servers SHOULD
         *      NOT send Expires dates more than one year in the future.
         */
        Date oneYear = new Date(new Date().getTime() + 365 * 86400 * 1000000);
        response.setHeader("Expires", HTTPDates.RFC1123.format(oneYear));

        /**
         * @see RFC 2616 14.29
         *      <p>
         *      HTTP/1.1 servers SHOULD send Last-Modified whenever feasible
         */
        Date lastModified = blob.getLastModified();
        response.setHeader("Last-Modified", HTTPDates.RFC1123.format(lastModified));

        /**
         * Stream the content.
         */
        try {
            InputStream in = blob.newInputStream();
            ServletOutputStream out = response.getOutputStream();

            InputStreamSource source = new InputStreamSource(in);
            OutputStreamTarget target = new OutputStreamTarget(out);
            target.forWrite().writeBytes(source);

            // byte[] block = new byte[4096];
            // int cb;
            // while ((cb = in.read(block)) != -1) {
            // out.write(block, 0, cb);
            // }
            //
            // out.flush();
        } catch (IOException e) {
            throw e;
        }

        // Nothing more.
        return null;
    }

}
