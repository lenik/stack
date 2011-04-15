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
@RequestMapping(AllowListController.PREFIX + "*")
public class AllowListController
        extends GenericController {

    public static final String PREFIX = SEMProcessModule.PREFIX + "/list/";

    @Inject
    CommonDataManager dataManager;

    @Inject
    AllowListDao allowListDao;

    @Inject
    PrincipalDao principalDao;

    @Inject
    UserDao userDao;

    @RequestMapping("content.htm")
    public Map<String, Object> content(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));

        AllowList entity = allowListDao.load(id);

        AllowListDto dto = new AllowListDto(AllowListDto.RESPONSIBLES).marshal(entity);

        ModelMap modelMap = new ModelMap();
        modelMap.put("it", dto);
        return modelMap;
    }

    @RequestMapping("data.htm")
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

    @Override
    protected Map<String, Object> form(HttpServletRequest req, HttpServletResponse resp)
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

    @Override
    protected ModelAndView createOrUpdate(HttpServletRequest req, HttpServletResponse resp)
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

        return new ModelAndView(viewOf("index"));
    }

    @RequestMapping("delete.htm")
    public String delete(HttpServletRequest req, HttpServletResponse resp)
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
                return null;
            }

        return viewOf("index");
    }

}
