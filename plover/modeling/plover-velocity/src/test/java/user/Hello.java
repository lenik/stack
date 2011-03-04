package user;

import java.io.PrintWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class Hello {

    static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {
        VelocityEngine ve = new VelocityEngine();

        ve.setProperty("file.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();

        VelocityContext context = new VelocityContext();
        context.put("name", "lenik");

        Template tmpl = ve.getTemplate("user/Hello.vm");
        tmpl.merge(context, out);
        out.flush();
    }

}
