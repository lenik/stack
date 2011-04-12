package com.bee32.plover.velocity.service;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.bee32.plover.restful.IRESTfulRequest;
import com.bee32.plover.restful.IRESTfulResponse;
import com.bee32.plover.restful.RESTfulView;
import com.bee32.plover.velocity.VelocityUtil;

@Lazy
@Controller
public class VelocityView
        extends RESTfulView {

    @Override
    public int getPriority() {
        return 10; // Low.
    }

    @Override
    public boolean render(Class<?> clazz, Object obj, IRESTfulRequest req, IRESTfulResponse resp)
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
