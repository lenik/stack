package com.bee32.plover.faces;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ServiceLoader;

import org.apache.myfaces.renderkit.ErrorPageRenderer;
import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.faces.PloverErrorPageRenderer;

public class PloverErrorPageRendererTest
        extends Assert {

    @Test
    public void testHighlight() {
        String src = "java.lang.String\n    com.bee32.abc.def (line 3)\nEOT";
        String exp = "java.lang.String\n    <b>com.bee32.abc.def</b> (line 3)\nEOT";
        String actual = PloverErrorPageRenderer.highlight(src);
        assertEquals(exp, actual);
    }

    public static void main(String[] args)
            throws IOException {
        PloverErrorPageRenderer renderer = new PloverErrorPageRenderer();
        StringWriter buffer = new StringWriter();
        renderer.writeException(buffer, new Exception());
        System.out.println(buffer);

        System.out.println("Services: ");
        for (ErrorPageRenderer epr : ServiceLoader.load(ErrorPageRenderer.class)) {
            System.out.println("EPR: " + epr);
        }
    }

}
