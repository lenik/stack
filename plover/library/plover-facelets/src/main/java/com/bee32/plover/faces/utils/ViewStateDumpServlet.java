package com.bee32.plover.faces.utils;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import javax.free.JavaioFile;
import javax.free.XMLs;

import org.apache.commons.codec.binary.Base64;

import com.bee32.plover.html.HtmlTemplate;
import com.bee32.plover.site.SimpleServlet;

public class ViewStateDumpServlet
        extends SimpleServlet {

    private static final long serialVersionUID = 1L;

    public ViewStateDumpServlet() {
        pages.add("index", DumpAsXML.class);
    }

    public static class DumpAsXML
            extends HtmlTemplate {

        @Override
        protected void _pageContent()
                throws Exception {
            String fileName = getRequest().getParameter("file");
            if (fileName == null)
                throw new NullPointerException("fileName");

            JavaioFile file = new JavaioFile(fileName);
            if (file.exists() != Boolean.TRUE) {
                text("File doesn't exist: " + file);
                return;
            }

            String base64 = file.forRead().readTextContents();
            byte[] bin = Base64.decodeBase64(base64.getBytes());

            ByteArrayInputStream in = new ByteArrayInputStream(bin);
            ObjectInputStream objIn = new ObjectInputStream(in);
            Object obj;
            int index = 0;
            try {
                while ((obj = objIn.readObject()) != null) {
                    String fqcn = obj == null ? "(null)" : obj.getClass().getCanonicalName();
                    h3().text("Object " + index++ + ":  " + fqcn).end();

                    String xml = XMLs.encode(obj);
                    pre().text(xml).end();
                }
            } finally {
                in.close();
            }
        }
    }

}
