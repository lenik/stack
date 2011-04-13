package com.bee32.sem.process.verify.builtin;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.bee32.sem.process.verify.builtin.dao.AllowListDao;

@Controller
@Lazy
public class AllowListController
        extends MultiActionController {

    @Inject
    AllowListDao allowListDao;

    @RequestMapping("/process/verify/allowListIndex.htm")
    public void allowListIndex(HttpServletRequest req, HttpServletResponse resp) {

    }

}
