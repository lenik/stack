package com.bee32.plover.html;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.rtx.location.Location;
import com.bee32.plover.servlet.util.ThreadHttpContext;

public class SimpleRowImage
        extends HtmlBuilder {

    public SimpleRowImage() {
        super();
    }

    public SimpleRowImage(Writer writer) {
        super(writer);
    }

    public void put(String name, Location image, Location href) {
        HttpServletRequest request = ThreadHttpContext.getRequest();
        String _image = image.resolveAbsolute(request);

        tr();
        th().classAttr("key").text(name).end();
        td().classAttr("value");
        if (href != null) {
            String _href = href.resolveAbsolute(request);
            a().href(_href);
        }
        img().classAttr("icon").src(_image).end();
        if (href != null)
            end();
        end(2);
    }

}
