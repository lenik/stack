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

import com.bee32.plover.servlet.context.ITextForRequest;
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

    protected abstract void formatHeader(HttpServletRequest req, IIndentedOut out)
            throws IOException;

    protected abstract void formatFooter(HttpServletRequest req, IIndentedOut out)
            throws IOException;

    @Override
    public void format(HttpServletRequest req, IIndentedOut out)
            throws IOException {

        formatHeader(req, out);

        if (contents != null) {
            out.enter();

            for (Object obj : contents) {
                String str;
                if (obj instanceof ITextForRequest)
                    str = ((ITextForRequest) obj).resolve(req);
                else
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

        formatFooter(req, out);
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
    public <T> T dump(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setCharacterEncoding("utf-8");

        PrintWriter out = resp.getWriter();
        IndentedOutImpl _out = new IndentedOutImpl(new WriterPrintOut(out));

        format(req, _out);
        return null;
    }

    @Override
    public String toString() {
        PrettyPrintStream out = new PrettyPrintStream();

        try {
            format(null, out);
        } catch (IOException e) {
            out.println("Format Error: " + e);
            e.printStackTrace();
        }

        return out.toString();
    }

}
