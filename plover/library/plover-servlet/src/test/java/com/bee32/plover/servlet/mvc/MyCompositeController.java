package com.bee32.plover.servlet.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.servlet.util.ServletDiag;

@Controller
@Lazy
@RequestMapping(MyCompositeController.PREFIX + "**")
public class MyCompositeController
        extends CompositeController {

    public static final String PREFIX = "/my/";

    /**
     * DUMP: See also
     */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        return ServletDiag.dump(req, resp);
    }

}
