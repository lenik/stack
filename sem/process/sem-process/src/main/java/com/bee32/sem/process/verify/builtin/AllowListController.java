package com.bee32.sem.process.verify.builtin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.dao.PrincipalDao;
import com.bee32.icsf.principal.dto.PrincipalDto;
import com.bee32.plover.ajax.JsonUtil;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.builtin.dao.AllowListDao;

@Controller
@Lazy
public class AllowListController
        extends MultiActionController {

    public static final String PREFIX = SEMProcessModule.PREFIX + "/list/";

    @Inject
    AllowListDao allowListDao;

    @Inject
    PrincipalDao principalDao;

    @RequestMapping(PREFIX + "index.htm")
    public Map<String, Object> index(HttpServletRequest req, HttpServletResponse resp) {
        ModelMap mm = new ModelMap();

        // Index by data-table:
        // List<AllowListDto> list = AllowListDto.marshalList(0, allowListDao.list());
        // mm.put("list", list);

        return mm;
    }

    @RequestMapping(PREFIX + "data.htm")
    public void data(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        DataTableDxo opts = new DataTableDxo();
        opts.parse(req);

        List<AllowListDto> all = AllowListDto.marshalList(//
                AllowListDto.RESPONSIBLES, allowListDao.list());

        opts.totalRecords = all.size();
        opts.totalDisplayRecords = opts.totalRecords;

        List<Object[]> rows = new ArrayList<Object[]>();

        for (AllowListDto alist : all) {
            Object[] row = new Object[3];
            row[0] = alist.getId();
            row[1] = alist.getName();

            int max = 3;
            StringBuilder names = null;
            for (PrincipalDto<?> responsible : alist.getResponsibles()) {
                if (max <= 0) {
                    names.append(", etc.");
                    break;
                }

                if (names == null)
                    names = new StringBuilder();
                else
                    names.append(", ");

                names.append(responsible.getName());

                max--;
            }
            row[2] = names == null ? "" : names.toString();

            rows.add(row);
        }

        opts.data = rows;

        JsonUtil.dump(resp, opts.exportMap());
    }

    @RequestMapping(PREFIX + "createForm.htm")
    public Map<?, ?> createForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        req.setAttribute("create", true);
        return form(req, resp);
    }

    @RequestMapping(PREFIX + "editForm.htm")
    public Map<?, ?> editForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        req.setAttribute("create", false);
        return form(req, resp);
    }

    Map<String, Object> form(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {

        boolean create = (Boolean) req.getAttribute("create");

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("_create", create);
        map.put("_verb", create ? "Create" : "Modify");

        if (!create) {
            int id = Integer.parseInt(req.getParameter("id"));
            AllowList entity = allowListDao.load(id);
            AllowListDto dto = new AllowListDto(AllowListDto.RESPONSIBLES).marshal(entity);
            map.put("it", dto);
        }

        return map;
    }

    @RequestMapping(PREFIX + "create.htm")
    public void create(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("create", true);
        createOrUpdate(req, resp);
    }

    @RequestMapping(PREFIX + "update.htm")
    public void update(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("create", false);
        createOrUpdate(req, resp);
    }

    void createOrUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        boolean create = (Boolean) req.getAttribute("create");

        AllowListDto dto = new AllowListDto(AllowListDto.RESPONSIBLES);
        dto.parse(req);

        Integer id = dto.getId();
        if (id == null)
            throw new ServletException("id isn't specified");

        AllowList entity;
        if (create) {
            entity = new AllowList();
        } else {
            entity = allowListDao.get(id);
            if (entity == null)
                throw new IllegalStateException("No allow list whose id=" + id);

            Integer requestVersion = dto.getVersion();
            if (requestVersion != null && requestVersion != entity.getVersion()) {
                throw new IllegalStateException("Version obsoleted");
            }
        }

        { /* unmarshal */
            entity.setName(dto.name);
            Set<Principal> responsibles = new HashSet<Principal>();
            for (PrincipalDto<?> p : dto.responsibles) {
                Principal responsible = principalDao.get(p.getId());
                responsibles.add(responsible);
            }
            entity.setResponsibles(responsibles);
        }

        allowListDao.saveOrUpdate(entity);

        resp.sendRedirect("index.htm");
    }

    @RequestMapping(PREFIX + "delete.htm")
    public void delete(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {

        int id = Integer.parseInt(req.getParameter("id"));

        allowListDao.deleteByKey(id);

        resp.sendRedirect("index.htm");
    }

}
