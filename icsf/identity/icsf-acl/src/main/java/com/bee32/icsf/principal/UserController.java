package com.bee32.icsf.principal;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.restful.IRestfulRequest;
import com.bee32.plover.restful.IRestfulResponse;
import com.bee32.plover.restful.util.TemplateController;

@Scope("prototype")
@Lazy
@Controller
public class UserController
        extends TemplateController<User> {

    @Inject
    CommonDataManager dataManager;

    public UserController() {
        super(User.class);
    }

    @Override
    protected void template(int mode, IRestfulRequest req, IRestfulResponse resp)
            throws Exception {
    }

    @Transactional(readOnly = true)
    public void content(IRestfulRequest req, IRestfulResponse resp)
            throws IOException {

        PrintWriter out = resp.getWriter();
        User user = cast(req);

        user = dataManager.load(User.class, user.getId());

        // dataManager.refresh(user);

        out.println("Template: " + user);
    }

}
