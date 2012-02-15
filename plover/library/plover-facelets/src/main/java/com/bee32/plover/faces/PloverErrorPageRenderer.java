package com.bee32.plover.faces;

import java.io.IOException;
import java.io.Writer;

import org.apache.myfaces.renderkit.DefaultErrorPageRenderer;

import com.bee32.plover.arch.logging.ExceptionFormat;

public class PloverErrorPageRenderer
        extends DefaultErrorPageRenderer {

    @Override
    public void writeException(Writer writer, Throwable e)
            throws IOException {
        String html = ExceptionFormat.highlight(e);
        writer.write(html);
    }

}
