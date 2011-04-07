package com.bee32.plover.velocity.service;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.bee32.plover.restful.IRestfulRequest;
import com.bee32.plover.restful.IRestfulResponse;
import com.bee32.plover.restful.RestfulView;
import com.bee32.plover.velocity.VelocityUtil;

public class VelocityView
        extends RestfulView {

    @Override
    public int getPriority() {
        return 10; // Low.
    }

    @Override
    public boolean render(Class<?> clazz, Object obj, IRestfulRequest req, IRestfulResponse resp)
            throws IOException {
        String view = req.getMethod();

        Template template = VelocityUtil.getTemplate(clazz, view);
        if (template == null)
            return false;

        VelocityContext context = new VelocityContext();
        context.put("it", obj);
        PrintWriter writer = resp.getWriter();
        template.merge(context, writer);
        writer.flush();
        return true;
    }

}
