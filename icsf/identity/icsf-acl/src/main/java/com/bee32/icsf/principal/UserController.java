package com.bee32.icsf.principal;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.restful.IRESTfulRequest;
import com.bee32.plover.restful.IRESTfulResponse;
import com.bee32.plover.restful.util.TemplateController;

@Scope("prototype")
@Lazy
@Controller
public class UserController
        extends TemplateController<User> {

    public UserController() {
        super(User.class);
    }

    @Override
    protected void template(int mode, IRESTfulRequest req, IRESTfulResponse resp)
            throws Exception {
    }

    @Transactional(readOnly = true)
    public void content(IRESTfulRequest req, IRESTfulResponse resp)
            throws IOException {

        PrintWriter out = resp.getWriter();
        User user = cast(req);

        out.println("Template: " + user);
    }

}
