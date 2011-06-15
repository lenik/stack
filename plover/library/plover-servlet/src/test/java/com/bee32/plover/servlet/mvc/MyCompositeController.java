package com.bee32.plover.servlet.mvc;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.servlet.util.ServletDiag;

@Controller
@Lazy
@Scope("prototype")
@RequestMapping(MyCompositeController.PREFIX + "/*")
public class MyCompositeController
        extends CompositeController {

    public static final String PREFIX = "/my";

    @Override
    protected void initController()
            throws BeansException {
        addHandler("dump2", new DumpAction());
        addHandler(new DumpAction());
    }

}

class DumpAction
        extends ActionHandler {

    @Override
    public ActionResult handleRequest(ActionRequest req, ActionResult result)
            throws Exception {
        HttpServletResponse resp = result.getResponse();
        return ServletDiag.dump(req, resp);
    }

}
