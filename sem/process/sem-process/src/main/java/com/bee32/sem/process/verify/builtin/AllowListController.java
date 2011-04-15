package com.bee32.sem.process.verify.builtin;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.dao.PrincipalDao;
import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.ajax.JsonUtil;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.builtin.dao.AllowListDao;

@Controller
@Lazy
public class AllowListController
        extends MultiActionController {

    public static final String PREFIX = SEMProcessModule.PREFIX + "/list/";

    @Inject
    CommonDataManager dataManager;

    @Inject
    AllowListDao allowListDao;

    @Inject
    PrincipalDao principalDao;

    @Inject
    UserDao userDao;

    @RequestMapping(PREFIX + "index.htm")
    public Map<String, Object> index(HttpServletRequest req, HttpServletResponse resp) {
        ModelMap mm = new ModelMap();

        // Index by data-table:
        // List<AllowListDto> list = AllowListDto.marshalList(0, allowListDao.list());
        // mm.put("list", list);

        return mm;
    }

    @RequestMapping(PREFIX + "content.htm")
    public Map<String, Object> content(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));

        AllowList entity = allowListDao.load(id);

        AllowListDto dto = new AllowListDto(AllowListDto.RESPONSIBLES).marshal(entity);

        ModelMap modelMap = new ModelMap();
        modelMap.put("it", dto);
        return modelMap;
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
            Object[] row = new Object[5 + 1];
            row[0] = alist.getId();
            row[1] = alist.getVersion();
            row[2] = alist.getName();
            row[3] = alist.getDescription();

            int max = 3;
            StringBuilder names = null;
            for (String responsible : alist.getResponsibleNames()) {
                if (max <= 0) {
                    names.append(", etc.");
                    break;
                }

                if (names == null)
                    names = new StringBuilder();
                else
                    names.append(", ");

                names.append(responsible);

                max--;
            }
            row[4] = names == null ? "" : names.toString();

            rows.add(row);
        }

        opts.data = rows;

        JsonUtil.dump(resp, opts.exportMap());
    }

    @RequestMapping(PREFIX + "createForm.htm")
    public ModelAndView createForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        req.setAttribute("create", true);

        Map<String, Object> map = form(req, resp);

        map.put("verb", "create");
        map.put("verb_en", "Create");
        map.put("verb_zh", "创建");

        return new ModelAndView(PREFIX + "form", map);
    }

    @RequestMapping(PREFIX + "editForm.htm")
    public ModelAndView editForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        req.setAttribute("create", false);

        Map<String, Object> map = form(req, resp);

        map.put("verb", "update");
        map.put("verb_en", "Edit");
        map.put("verb_zh", "编辑");

        return new ModelAndView(PREFIX + "form", map);
    }

    Map<String, Object> form(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {

        boolean create = (Boolean) req.getAttribute("create");

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("_create", create);
        map.put("_verb", create ? "Create" : "Modify");

        AllowListDto dto;
        if (create) {
            dto = new AllowListDto();
            dto.setName("");
            dto.setDescription("");
            dto.setResponsibleIds(new ArrayList<Long>());
            map.put("it", dto);
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            AllowList entity = allowListDao.load(id);
            dto = new AllowListDto(AllowListDto.RESPONSIBLES).marshal(entity);
        }
        map.put("it", dto);

        List<Object> users = UserDto.marshalList(0, userDao.list());
        map.put("users", users);

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

        AllowList entity;
        if (create) {
            entity = new AllowList();
        } else {
            Integer id = dto.getId();
            if (id == null)
                throw new ServletException("id isn't specified");

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
            entity.setDescription(dto.description);

            Set<Principal> responsibles = new HashSet<Principal>();
            for (Long responsibleId : dto.getResponsibleIds()) {
                Principal responsible = principalDao.get(responsibleId);
                responsibles.add(responsible);
            }
            entity.setResponsibles(responsibles);
        }

        dataManager.saveOrUpdate(entity);

        resp.sendRedirect("index.htm");
    }

    @RequestMapping(PREFIX + "delete.htm")
    public void delete(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {

        int id = Integer.parseInt(req.getParameter("id"));

        AllowList allowList = allowListDao.get(id);
        if (allowList != null)
            try {
                dataManager.delete(allowList);
            } catch (DataIntegrityViolationException e) {
                resp.setCharacterEncoding("utf-8");

                PrintWriter out = resp.getWriter();

                String message = "白名单策略 " + allowList.getName() + " 正在被其它对象使用中，删除失败。";
                out.println("<script language='javascript'>");
                out.println("alert('" + message + "'); ");
                out.println("history.back(); ");
                out.println("</script>");
                return;
            }

        resp.sendRedirect("index.htm");
    }

}
