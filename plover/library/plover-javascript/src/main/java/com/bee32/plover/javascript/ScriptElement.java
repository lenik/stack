package com.bee32.plover.javascript;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.free.IIndentedOut;
import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.servlet.context.ITextForRequest;
import com.bee32.plover.util.PrettyPrintStream;

public abstract class ScriptElement
        extends Dependent<IScriptElement>
        implements IScriptElement {

    List<Object> contents;

    public ScriptElement() {
    }

    public synchronized ScriptElement append(ITextForRequest text) {
        if (text == null)
            throw new NullPointerException("text");

        if (contents == null) {
            synchronized (this) {
                if (contents == null) {
                    contents = new ArrayList<Object>();
                }
            }
        }

        contents.add(text);
        return this;
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
