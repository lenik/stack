package com.bee32.zebra.tk.util;

import org.json.JSONWriter;

import net.bodz.bas.io.BCharOut;
import net.bodz.bas.xml.dom.IXmlNode;
import net.bodz.bas.xml.dom.IXmlTag;
import net.bodz.bas.xml.dom.XmlFormatter;
import net.bodz.bas.xml.dom.XmlNodeType;

public class Table2JsonFormatter {

    private JSONWriter out;
    private String nullVerbatim = XmlFormatter.NULL_VERBATIM;

    public Table2JsonFormatter(JSONWriter out) {
        if (out == null)
            throw new NullPointerException("out");
        this.out = out;
    }

    public String getNullVerbatim() {
        return nullVerbatim;
    }

    public void setNullVerbatim(String nullVerbatim) {
        this.nullVerbatim = nullVerbatim;
    }

    public void format(IXmlTag tag) {
        if (tag == null)
            throw new NullPointerException("tag");
        switch (tag.getTagName()) {
        case "thead":
        case "tbody":
        case "tfoot":
        case "tr":
            out.array();
            for (IXmlNode child : tag.getChildren())
                if (child.getType() == XmlNodeType.ELEMENT)
                    format((IXmlTag) child);
            out.endArray();
            break;

        case "th":
        case "td":
            BCharOut buf = new BCharOut();
            XmlFormatter fmt = new XmlFormatter(buf);
            for (IXmlNode child : tag.getChildren()) {
                fmt.format(child);
            }
            out.value(buf.toString());
            break;

        default:
            out.object();
            for (IXmlNode child : tag.getChildren())
                if (child.getType() == XmlNodeType.ELEMENT) {
                    IXmlTag childTag = (IXmlTag) child;
                    out.key(childTag.getTagName());
                    format(childTag);
                }
            out.endObject();
        }
    }

}
