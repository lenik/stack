package com.bee32.sem.process.verify.builtin.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import com.bee32.plover.arch.util.DTOs;
import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.builtin.AllowList;

@RequestMapping(AllowListController.PREFIX + "*")
public class AllowListController
        extends BasicEntityController<AllowList, Integer, AllowListDto> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "list/";

    @Inject
    PrincipalDao principalDao;

    @Inject
    UserDao userDao;

    @Override
    protected Integer content_getSelection() {
        return AllowListDto.RESPONSIBLES;
    }

    @Override
    protected void data_buildRow(DataTableDxo tab, AllowListDto item) {
        tab.push(item.getName());
        tab.push(item.getDescription());

        int max = 3;
        StringBuilder names = null;
        for (String responsible : item.getResponsibleNames()) {
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
    }

    @Override
    protected void create_template(AllowListDto dto) {
        dto.setName("");
        dto.setDescription("");
        dto.setResponsibleIds(new ArrayList<Long>());
    }

    @Override
    protected ModelAndView _createOrEditForm(ViewData view, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ModelAndView mav = super._createOrEdit(view, req, resp);

        List<UserDto> users = DTOs.marshalList(UserDto.class, 0, userDao.list());
        mav.addObject("users", users);

        return view;
    }

    @Override
    protected void doUnmarshal(AllowListDto dto, AllowList entity) {
        /* unmarshal */
        entity.setName(dto.name);
        entity.setDescription(dto.description);

        Set<Principal> responsibles = new HashSet<Principal>();
        for (Long responsibleId : dto.getResponsibleIds()) {
            Principal responsible = principalDao.get(responsibleId);
            responsibles.add(responsible);
        }
        entity.setResponsibles(responsibles);
    }

}
