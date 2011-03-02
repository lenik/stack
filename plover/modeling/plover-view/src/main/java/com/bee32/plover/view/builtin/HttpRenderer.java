package com.bee32.plover.view.builtin;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import com.bee32.plover.view.ContentRenderer;
import com.bee32.plover.view.IDisplay;
import com.bee32.plover.view.RenderException;

public class HttpRenderer
        extends ContentRenderer {

    @Override
    public boolean isRenderable(Object obj) {
        return obj instanceof HttpServlet;
    }

    @Override
    public void render(Object obj, IDisplay display)
            throws RenderException {
        HttpServlet servlet = (HttpServlet) obj;
        ServletResponse response = display.getResponse();
        servlet.service(req, response);
    }

}
