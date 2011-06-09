package com.bee32.plover.servlet.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.servlet.util.ServletDiag;

@Controller
@Lazy
@Scope("prototype")
@RequestMapping(MyCompositeController.PREFIX + "/**")
public class MyCompositeController
        extends CompositeController {

    public static final String PREFIX = "/my";

    public MyCompositeController() {
        addAction("dump2", new DumpAction());
        addAction(new DumpAction());
    }

}

class DumpAction
        extends ActionHandler {

    @Override
    public ActionResult handleRequest(HttpServletRequest req, ActionResult view)
            throws Exception {
        HttpServletResponse resp = view.getResponse();
        return ServletDiag.dump(req, resp);
    }

}
