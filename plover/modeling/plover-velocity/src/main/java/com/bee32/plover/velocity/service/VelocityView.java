package com.bee32.plover.velocity.service;

import java.io.PrintWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.bee32.plover.restful.IRestfulRequest;
import com.bee32.plover.restful.IRestfulResponse;

public class VelocityView {

    public boolean render(Object obj, IRestfulRequest req, IRestfulResponse resp) {
        return render(obj.getClass(), obj, req, resp);
    }

    public boolean render(Class<?> clazz, Object obj, IRestfulRequest req, IRestfulResponse resp) {
        String view = "";
        Template template = Velocity.getTemplate(clazz, view);
        if (template != null) {
            VelocityContext context = new VelocityContext();
            context.put("it", obj);
            PrintWriter writer = resp.getWriter();
            template.merge(context, writer);
            writer.flush();
            return true;
        }
    }

}
