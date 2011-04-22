package com.bee32.sem.process.verify.builtin.web;

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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.dao.PrincipalDao;
import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.ajax.JsonUtil;
import com.bee32.plover.arch.util.DTOs;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.plover.orm.ext.util.EntityController;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.builtin.AllowList;
import com.bee32.sem.process.verify.builtin.dao.AllowListDao;

@RequestMapping(AllowListController.PREFIX + "*")
public class AllowListController
        extends EntityController<AllowList, Integer> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "/list/";

    @Inject
    AllowListDao allowListDao;

    @Inject
    PrincipalDao principalDao;

    @Inject
    UserDao userDao;

    @Override
    protected void preamble(Map<String, Object> metaData) {
        metaData.put(ENTITY_TYPE_NAME, "白名单策略");
    }

    @Override
    protected Map<String, Object> _content(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        AllowList entity = allowListDao.load(id);

        return it(new AllowListDto(entity, AllowListDto.RESPONSIBLES));
    }

    @Override
    public void _data(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        DataTableDxo tab = new DataTableDxo(req);

        List<AllowListDto> all = DTOs.marshalList(AllowListDto.class, //
                AllowListDto.RESPONSIBLES, allowListDao.list());

        tab.totalRecords = all.size();
        tab.totalDisplayRecords = tab.totalRecords;

        for (AllowListDto alist : all) {
            tab.push(alist.getId());
            tab.push(alist.getVersion());
            tab.push(alist.getName());
            tab.push(alist.getDescription());

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
            tab.push(names == null ? "" : names.toString());
            tab.next();
        }

        JsonUtil.dump(resp, tab.exportMap());
    }

    @Override
    protected Map<String, Object> _createOrEditForm(HttpServletRequest req, HttpServletResponse resp)
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

        List<UserDto> users = DTOs.marshalList(UserDto.class, 0, userDao.list());
        map.put("users", users);

        return map;
    }

    @Override
    protected ModelAndView _createOrUpdate(HttpServletRequest req, HttpServletResponse resp)
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

}
