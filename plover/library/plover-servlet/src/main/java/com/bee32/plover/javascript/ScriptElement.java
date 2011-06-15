package com.bee32.plover.javascript;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.free.IIndentedOut;
import javax.free.IndentedOutImpl;
import javax.free.WriterPrintOut;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bee32.plover.rtx.location.ITextForRequest;
import com.bee32.plover.servlet.mvc.ActionResult;
import com.bee32.plover.servlet.util.ThreadServletContext;
import com.bee32.plover.util.PrettyPrintStream;

public abstract class ScriptElement
        extends Dependent<IScriptElement>
        implements IScriptElement {

    List<Object> contents;

    public ScriptElement() {
    }

    public ScriptElement append(Object content) {
        if (content == null)
            throw new NullPointerException("content");

        if (contents == null) {
            synchronized (this) {
                if (contents == null) {
                    contents = new ArrayList<Object>();
                }
            }
        }

        contents.add(content);
        return this;
    }

    public void print(Object content) {
        append(content);
    }

    public void println(Object content) {
        append(content);
        append("\n");
    }

    protected abstract void formatHeader(IIndentedOut out)
            throws IOException;

    protected abstract void formatFooter(IIndentedOut out)
            throws IOException;

    @Override
    public void format(IIndentedOut out)
            throws IOException {

        formatHeader(out);

        if (contents != null) {
            out.enter();

            for (Object obj : contents) {
                String str;
                if (obj instanceof ITextForRequest) {
                    HttpServletRequest req = ThreadServletContext.requireRequest();
                    str = ((ITextForRequest) obj).resolve(req);
                } else
                    str = obj.toString();

                StringTokenizer lines = new StringTokenizer(str, "\n");
                if (lines.hasMoreTokens()) {
                    out.print(lines.nextToken());

                    while (lines.hasMoreTokens()) {
                        out.println();
                        out.print(lines.nextToken());
                    }
                }
            }

            out.leave();
        }

        formatFooter(out);
    }

    /**
     * Dump this element to response and quit.
     *
     * @param req
     *            The servlet request object from which the location context is resolved.
     * @param resp
     *            The response where this element will be dumped.
     * @return Always return <code>null</code>.
     */
    public <T> T dump(HttpServletResponse resp)
            throws IOException {
        resp.setCharacterEncoding("utf-8");

        PrintWriter out = resp.getWriter();
        IndentedOutImpl _out = new IndentedOutImpl(new WriterPrintOut(out));

        format(_out);
        return null;
    }

    public <T> T dump(ActionResult result)
            throws IOException {
        return dump(result.getResponse());
    }

    @Override
    public String toString() {
        PrettyPrintStream out = new PrettyPrintStream();

        try {
            format(out);
        } catch (IOException e) {
            out.println("Format Error: " + e);
            e.printStackTrace();
        }

        return out.toString();
    }

}
