package com.bee32.sem.process.verify.builtin;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
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
    @Transactional(readOnly = true)
    public ModelMap allowListIndex(HttpServletRequest req, HttpServletResponse resp) {
        ModelMap mm = new ModelMap();

        List<? extends AllowList> list = allowListDao.list();
        mm.put("list", list);

        return mm;
    }

}
