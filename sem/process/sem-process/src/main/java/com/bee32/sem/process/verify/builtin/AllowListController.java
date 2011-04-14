package com.bee32.sem.process.verify.builtin;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.builtin.dao.AllowListDao;
import com.google.gson.Gson;

@Controller
@Lazy
public class AllowListController
        extends MultiActionController {

    static final String PREFIX = SEMProcessModule.PREFIX + "/list/";

    @Inject
    AllowListDao allowListDao;

    @RequestMapping(SEMProcessModule.PREFIX + "index.htm")
    public Map<String, Object> allowListIndex(HttpServletRequest req, HttpServletResponse resp) {
        ModelMap mm = new ModelMap();

        List<? extends AllowList> list = allowListDao.list();
        mm.put("list", list);

        return mm;
    }

    @RequestMapping(PREFIX + "tableJson.htm")
    public void tableJson(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        DataTableDxo opts = new DataTableDxo();
        opts.parse(req);

        opts.totalRecords = allowListDao.count();
        opts.totalDisplayRecords = opts.totalRecords;

        List<Object[]> rows = new ArrayList<Object[]>();

        for (AllowList alist : allowListDao.list()) {
            Object[] row = new Object[2];
            row[0] = alist.getId();
            row[1] = alist.getName();
            // alist.getResponsibles();
            rows.add(row);
        }

        opts.data = rows;

        resp.setContentType("application/json; charset=UTF-8");
        resp.setHeader("Cache-Control", "no-cache");
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(opts.exportMap()));
        resp.flushBuffer();
        resp.getWriter().close();
    }

    @RequestMapping(PREFIX + "save.htm")
    public void save(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ParseException {

        String mode = request.getParameter("mode");

        response.sendRedirect("index.htm");
    }

    @RequestMapping(PREFIX + "add.htm")
    public Map<?, ?> addMaterial(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        return map;
    }

    @RequestMapping(PREFIX + "update.htm")
    public Map<?, ?> modifyMaterial(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        return map;
    }

    @RequestMapping(PREFIX + "delete.htm")
    public void deleteMaterial(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.sendRedirect("delete.htm");
    }

}
